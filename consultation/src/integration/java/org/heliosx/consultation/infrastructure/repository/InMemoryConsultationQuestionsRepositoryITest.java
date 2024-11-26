package org.heliosx.consultation.infrastructure.repository;

import org.heliosx.consultation.utils.FileUtils;
import org.heliosx.consultation.utils.JsonMapper;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

class InMemoryConsultationQuestionsRepositoryITest {

    private final InMemoryConsultationQuestionsRepository underTest = new InMemoryConsultationQuestionsRepository();

    @Test
    void itShouldObtainTheQuestionsRelatedToAcne() {
        var expectedRequest = FileUtils.readFileFromResources("consultation-questions/acne.json");

        var actualResponse = underTest.getQuestionsForConsultation("acne");

        JSONAssert.assertEquals(expectedRequest, JsonMapper.fromObject(actualResponse), true);
    }
}
