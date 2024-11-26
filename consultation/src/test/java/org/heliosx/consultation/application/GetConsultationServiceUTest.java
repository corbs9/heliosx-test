package org.heliosx.consultation.application;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.heliosx.consultation.utils.RandomString.getRandomString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.heliosx.consultation.domain.model.ConsultationQuestions;
import org.heliosx.consultation.domain.repository.ConsultationQuestionsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GetConsultationServiceUTest {

    private GetConsultationService underTest;
    private String consultationName;
    private ConsultationQuestionsRepository repo;

    @BeforeEach
    void setupTest() {
        repo = mock(ConsultationQuestionsRepository.class);
        underTest = new GetConsultationService(repo);
        consultationName = getRandomString();
    }

    @Test
    void itShouldObtainTheConsultationQuestionsFromTheRequest() {
        given(repo.getQuestionsForConsultation(consultationName)).willReturn(new ConsultationQuestions(emptyList()));

        underTest.perform(consultationName);

        verify(repo).getQuestionsForConsultation(consultationName);
    }

    @Test
    void itShouldReturnTheConsultationQuestionsResponse() {
        var expectedResponse = givenAConsultationQuestionsResponse();
        given(repo.getQuestionsForConsultation(consultationName)).willReturn(expectedResponse);

        var actualResponse = underTest.perform(consultationName);

        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    private ConsultationQuestions givenAConsultationQuestionsResponse() {
        return new ConsultationQuestions(emptyList());
    }
}
