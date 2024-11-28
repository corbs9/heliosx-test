package org.heliosx.consultation.infrastructure.web.controller;

import static java.util.Collections.emptyList;
import static org.heliosx.consultation.domain.model.QuestionType.YESORNO;
import static org.heliosx.consultation.utils.RandomEnum.getRandomEnum;
import static org.heliosx.consultation.utils.RandomNumber.getRandomLong;
import static org.heliosx.consultation.utils.RandomString.getRandomString;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.heliosx.consultation.AssessConsultation;
import org.heliosx.consultation.GetConsultation;
import org.heliosx.consultation.domain.model.*;
import org.heliosx.consultation.utils.JsonMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.http.MediaType;
import org.springframework.test.json.JsonCompareMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class ConsultationControllerITest {
    private final String consultationUri = "/consultation/acne";
    private final String consultationName = "acne";
    private final GetConsultation getConsultation = mock(GetConsultation.class);
    private final AssessConsultation assessConsultation = mock(AssessConsultation.class);
    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(
                    new ConsultationController(getConsultation, assessConsultation))
            .build();

    @Nested
    class GetConsultationTests {

        @Test
        void itShouldReturnA200OKResponse() throws Exception {
            given(getConsultation.perform(consultationName)).willReturn(new ConsultationQuestions(emptyList()));

            mockMvc.perform(get(consultationUri)).andExpect(status().is(200));
        }

        @Test
        void itShouldReturnAnEmptyResponseIfNoQuestionsAreFound() throws Exception {
            var expectedResponse = Map.of("questionGroups", List.of());

            given(getConsultation.perform(consultationName)).willReturn(new ConsultationQuestions(emptyList()));

            var actualResponse = mockMvc.perform(get(consultationUri))
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            JSONAssert.assertEquals(JsonMapper.fromObject(expectedResponse), actualResponse, true);
        }

        @Test
        void itShouldReturnAQuestionGroupWithRelatedQuestions() throws Exception {
            var questions = List.of(givenAQuestion());
            var questionGroups = List.of(givenAQuestionGroupWithQuestions(questions));
            given(getConsultation.perform(consultationName)).willReturn(new ConsultationQuestions(questionGroups));

            var actualResponse = mockMvc.perform(get(consultationUri))
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            var expectedResponse = createExpectedResponseFromQuestionGroups(questionGroups);
            JSONAssert.assertEquals(JsonMapper.fromObject(expectedResponse), actualResponse, false);
        }

        private Map<Object, Object> createExpectedResponseFromQuestionGroups(List<QuestionGroup> questionGroups) {
            return Map.of(
                    "questionGroups",
                    questionGroups.stream()
                            .map(group -> Map.of(
                                    "id", group.id(),
                                    "name", group.name(),
                                    "questions",
                                            group.questions().stream()
                                                    .map(question -> Map.of(
                                                            "id",
                                                            question.id(),
                                                            "name",
                                                            question.name(),
                                                            "questionType",
                                                            question.questionType()))
                                                    .toList()))
                            .toList());
        }

        private Question givenAQuestion() {
            return new Question(getRandomLong(), getRandomString(), YESORNO);
        }

        private QuestionGroup givenAQuestionGroupWithQuestions(Collection<Question> questions) {
            return new QuestionGroup(getRandomLong(), getRandomString(), questions);
        }
    }

    @Nested
    class AssessConsultationTests {

        @Test
        void itShouldReturnA200OK() throws Exception {
            givenAnAnalysedConsultation();

            mockMvc.perform(post(consultationUri)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(givenAnAnalyseConsultationRequest()))
                    .andExpect(status().is(200));
        }

        @Test
        void itShouldAcceptAnAnalyseConsultationRequest() throws Exception {
            var expectedRequest = givenAnAnalyseConsultationRequest();
            givenAnAnalysedConsultation();

            mockMvc.perform(post(consultationUri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(expectedRequest));

            var argumentCaptor = ArgumentCaptor.forClass(AnalyseConsultationRequest.class);
            verify(assessConsultation).perform(eq(consultationName), argumentCaptor.capture());
            var actualRequest = JsonMapper.fromObject(argumentCaptor.getValue());

            System.out.println(expectedRequest);
            System.out.println(actualRequest);

            JSONAssert.assertEquals(expectedRequest, actualRequest, JSONCompareMode.NON_EXTENSIBLE);
        }

        @Test
        void itShouldReturnTheResultOfTheAnalysis() throws Exception {
            var questionResponses = List.of(givenAnAnalyseQuestionResponse());
            var questionGroupResponses =
                    List.of(givenAnAnalyseQuestionGroupWithAnalyseQuestionsResponses(questionResponses));
            var consultationStatus = getRandomEnum(ErrorLevel.class);
            given(assessConsultation.perform(anyString(), any()))
                    .willReturn(new AnalysedConsultation(consultationStatus, questionGroupResponses));

            var expectedResponse = JsonMapper.fromObject(
                    createExpectedResponseFromQuestionGroupResponses(consultationStatus, questionGroupResponses));

            mockMvc.perform(post(consultationUri)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(givenAnAnalyseConsultationRequest()))
                    .andExpect(content().json(expectedResponse, JsonCompareMode.LENIENT));
        }

        private void givenAnAnalysedConsultation() {
            var questionResponses = List.of(givenAnAnalyseQuestionResponse());
            var questionGroupResponses =
                    List.of(givenAnAnalyseQuestionGroupWithAnalyseQuestionsResponses(questionResponses));
            given(assessConsultation.perform(anyString(), any()))
                    .willReturn(new AnalysedConsultation(getRandomEnum(ErrorLevel.class), questionGroupResponses));
        }

        private Map<String, Object> createExpectedResponseFromQuestionGroupResponses(
                ErrorLevel errorLevel, Collection<AnalysedQuestionGroup> questionGroupResponses) {
            return Map.of(
                    "status",
                    errorLevel,
                    "questionGroups",
                    questionGroupResponses.stream()
                            .map(group -> Map.of(
                                    "id",
                                    group.id(),
                                    "questions",
                                    group.questions().stream()
                                            .map(question -> Map.of(
                                                    "id",
                                                    question.id(),
                                                    "status",
                                                    question.status().name(),
                                                    "message",
                                                    question.message()))
                                            .toList()))
                            .toList());
        }

        private String givenAnAnalyseConsultationRequest() {
            return JsonMapper.fromObject(Map.of(
                    "questionGroups",
                    List.of(Map.of(
                            "id", getRandomLong(),
                            "questions",
                                    List.of(
                                            Map.of("id", getRandomLong(), "response", "YES"),
                                            Map.of("id", getRandomLong(), "response", "NO"))))));
        }

        private AnalysedQuestion givenAnAnalyseQuestionResponse() {
            return new AnalysedQuestion(getRandomLong(), getRandomEnum(ErrorLevel.class), getRandomString());
        }

        private AnalysedQuestionGroup givenAnAnalyseQuestionGroupWithAnalyseQuestionsResponses(
                Collection<AnalysedQuestion> analysedQuestionRespons) {
            return new AnalysedQuestionGroup(getRandomLong(), analysedQuestionRespons);
        }
    }
}
