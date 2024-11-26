package com.heliosx.consultation;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.heliosx.consultation.AssessConsultation;
import org.heliosx.consultation.config.ConsultationConfig;
import org.heliosx.consultation.domain.model.AnalyseConsultationRequest;
import org.heliosx.consultation.domain.model.AnalyseQuestion;
import org.heliosx.consultation.domain.model.AnalyseQuestionGroup;
import org.heliosx.consultation.domain.model.ErrorLevel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ConsultationConfig.class)
public class AssessConsultationFunTest {
    private final String consultationName = "acne";

    @Autowired
    AssessConsultation assessConsultation;

    @Test
    void itShouldReturnAnAnalysedConsultationForValidConsultationQuestions() {
        var group1Questions = new AnalyseQuestion(1L, "YES");
        var questionGroups = List.of(new AnalyseQuestionGroup(1L, List.of(group1Questions)));
        var request = new AnalyseConsultationRequest(questionGroups);

        var response = assessConsultation.perform(consultationName, request);

        assertThat(response.status()).isEqualTo(ErrorLevel.OK);
    }

    @Test
    void itShouldReturnAFailedConsultationForAQuestionWhichIsRuledAsAnError() {
        var group1Questions = new AnalyseQuestion(1L, "NO");
        var questionGroups = List.of(new AnalyseQuestionGroup(1L, List.of(group1Questions)));
        var request = new AnalyseConsultationRequest(questionGroups);

        var response = assessConsultation.perform(consultationName, request);

        assertThat(response.status()).isEqualTo(ErrorLevel.ERROR);
    }
}
