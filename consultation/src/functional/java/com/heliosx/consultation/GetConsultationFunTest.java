package com.heliosx.consultation;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.heliosx.consultation.GetConsultation;
import org.heliosx.consultation.config.ConsultationConfig;
import org.heliosx.consultation.domain.exception.ConsultationNotFoundException;
import org.heliosx.consultation.domain.model.ConsultationQuestions;
import org.heliosx.consultation.utils.FileUtils;
import org.heliosx.consultation.utils.JsonMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ConsultationConfig.class)
class GetConsultationFunTest {

    @Autowired
    GetConsultation getConsultation;

    @Test
    void itShouldReturnConsultationQuestionsForAcne() {
        ConsultationQuestions actualResponse = getConsultation.perform("acne");

        String expectedResponse = FileUtils.readFileFromResources("consultation-questions/acne.json");

        JSONAssert.assertEquals(expectedResponse, JsonMapper.fromObject(actualResponse), true);
    }

    @Test
    void itShouldThrowAConsultationNameNotFoundExceptionWhenUnknown() {
        var request = "unknown";

        assertThrows(ConsultationNotFoundException.class, () -> getConsultation.perform(request));
    }
}
