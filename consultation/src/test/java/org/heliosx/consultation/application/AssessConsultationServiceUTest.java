package org.heliosx.consultation.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.heliosx.consultation.domain.model.ErrorLevel.ERROR;
import static org.heliosx.consultation.utils.RandomEnum.getRandomEnum;
import static org.heliosx.consultation.utils.RandomNumber.getRandomLong;
import static org.heliosx.consultation.utils.RandomString.getRandomString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.util.List;
import java.util.Map;
import org.heliosx.consultation.AssessConsultation;
import org.heliosx.consultation.domain.model.*;
import org.heliosx.consultation.domain.model.rules.ConsultationRules;
import org.heliosx.consultation.domain.model.rules.YesNoRule;
import org.heliosx.consultation.domain.repository.ConsultationRulesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class AssessConsultationServiceUTest {
    private AssessConsultation assessConsultation;
    private ConsultationRulesRepository consultationRulesRepository;
    private String consultationName;
    private String errorMessage;
    private long questionGroupId;
    private long questionId;
    private long anotherQuestionId;
    private final String emptyMessage = "";

    @BeforeEach
    void setup() {
        this.consultationName = getRandomString();
        this.errorMessage = getRandomString();
        this.questionGroupId = getRandomLong();
        this.questionId = getRandomLong();
        this.anotherQuestionId = getRandomLong();
        this.consultationRulesRepository = mock(ConsultationRulesRepository.class);
        this.assessConsultation = new AssessConsultationService(consultationRulesRepository);
    }

    @Test
    void itShouldIndicateThatConsultationWillBeSuccessfulIfAllRulesPass() {
        givenConsultationRulesFor("YES", ERROR, "YES", ERROR);

        var analyseConsultationReq = givenAnAnalyseConsultationRequestFor("YES");
        var analysedConsultation = assessConsultation.perform(consultationName, analyseConsultationReq);

        var expectedResponse = new AnalysedQuestion(questionId, ErrorLevel.OK, emptyMessage);

        assertThat(findAnalysedQuestionFrom(analysedConsultation)).isEqualTo(expectedResponse);
    }

    @Test
    void itShouldIndicateThatTheConsultationWillBeLimitedIfAWarningRuleApplies() {
        givenConsultationRulesFor("NO", ErrorLevel.WARNING, "NO", ErrorLevel.WARNING);

        var analyseConsultationReq = givenAnAnalyseConsultationRequestFor("YES");
        var analysedConsultation = assessConsultation.perform(consultationName, analyseConsultationReq);

        var expectedResponse = new AnalysedQuestion(questionId, ErrorLevel.WARNING, errorMessage);
        assertThat(findAnalysedQuestionFrom(analysedConsultation)).isEqualTo(expectedResponse);
    }

    @Test
    void itShouldIndicateThatTheConsultationWillFailIfAnErrorRuleApplies() {
        givenConsultationRulesFor("YES", ERROR, "YES", ERROR);

        var analyseConsultationReq = givenAnAnalyseConsultationRequestFor("NO");
        var analysedConsultation = assessConsultation.perform(consultationName, analyseConsultationReq);

        var expectedResponse = new AnalysedQuestion(questionId, ERROR, errorMessage);
        assertThat(findAnalysedQuestionFrom(analysedConsultation)).isEqualTo(expectedResponse);
    }

    @Test
    void itShouldNotCareAboutTheCaseOfTheResponse() {
        givenConsultationRulesFor("YeS", ERROR, "YeS", ERROR);

        var analyseConsultationReq = givenAnAnalyseConsultationRequestFor("nO");
        var analysedConsultation = assessConsultation.perform(consultationName, analyseConsultationReq);

        var expectedResponse = new AnalysedQuestion(questionId, ERROR, errorMessage);
        assertThat(findAnalysedQuestionFrom(analysedConsultation)).isEqualTo(expectedResponse);
    }

    @Test
    void itShouldStipulateThatTheWholeConsultationIsLikelyToBeAcceptedIfAllRulesPass() {
        givenConsultationRulesFor("YES", ERROR, "YES", ERROR);

        var analyseConsultationReq = givenAnAnalyseConsultationRequestFor("YES");
        var analysedConsultation = assessConsultation.perform(consultationName, analyseConsultationReq);

        assertThat(analysedConsultation.status()).isEqualTo(ErrorLevel.OK);
    }

    @Test
    void itShouldStipulateThatTheWholeConsultationIsToBeLimitedIfALimitedRuleApplies() {
        givenConsultationRulesFor("NO", ErrorLevel.WARNING, "NO", ErrorLevel.WARNING);

        var analyseConsultationReq = givenAnAnalyseConsultationRequestFor("YES");
        var analysedConsultation = assessConsultation.perform(consultationName, analyseConsultationReq);

        assertThat(analysedConsultation.status()).isEqualTo(ErrorLevel.WARNING);
    }

    @Test
    void itShouldStipulateTheConsultationIsToFailIfAnErrorRuleApplies() {
        givenConsultationRulesFor("YES", ERROR, "YES", ERROR);

        var analyseConsultationReq = givenAnAnalyseConsultationRequestFor("NO");
        var analysedConsultation = assessConsultation.perform(consultationName, analyseConsultationReq);

        assertThat(analysedConsultation.status()).isEqualTo(ERROR);
    }

    @RepeatedTest(20)
    void itShouldGiveTheOverallStatusAsTheMostSeriousRuleValidations() {
        var e1 = getRandomEnum(ErrorLevel.class);
        var e2 = getRandomEnum(ErrorLevel.class);

        givenConsultationRulesFor("NO", e1, "NO", e2);

        var req = givenAnAnalyseConsultationRequestFor("YES");
        var analysedConsultation = assessConsultation.perform(consultationName, req);

        System.out.println(e1);
        System.out.println(e2);

        var expectedErrorLevel = e1.priority() < e2.priority() ? e1 : e2;
        assertThat(analysedConsultation.status()).isEqualTo(expectedErrorLevel);
    }

    private void givenConsultationRulesFor(String resp1, ErrorLevel errorLevel1, String resp2, ErrorLevel errorLevel2) {
        var consultationRules = new ConsultationRules(Map.of(
                questionGroupId,
                Map.of(
                        questionId,
                        new YesNoRule(resp1, errorLevel1, errorMessage),
                        anotherQuestionId,
                        new YesNoRule(resp2, errorLevel2, errorMessage))));
        given(consultationRulesRepository.getRules(consultationName)).willReturn(consultationRules);
    }

    private AnalyseConsultationRequest givenAnAnalyseConsultationRequestFor(String response) {
        return new AnalyseConsultationRequest(List.of(new AnalyseQuestionGroup(
                questionGroupId,
                List.of(new AnalyseQuestion(questionId, response), new AnalyseQuestion(anotherQuestionId, response)))));
    }

    private AnalysedQuestion findAnalysedQuestionFrom(AnalysedConsultation analysedConsultation) {
        return analysedConsultation.questionGroups().stream()
                .filter(group -> group.id() == questionGroupId)
                .findFirst()
                .orElseThrow()
                .questions()
                .stream()
                .filter(question -> question.id() == questionId)
                .findFirst()
                .orElseThrow();
    }
}
