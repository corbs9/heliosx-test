package org.heliosx.consultation.infrastructure.repository;

import java.util.HashMap;
import java.util.Map;
import org.heliosx.consultation.domain.model.ConsultationQuestions;
import org.heliosx.consultation.domain.repository.ConsultationQuestionsRepository;
import org.heliosx.consultation.utils.FileUtils;
import org.heliosx.consultation.utils.JsonMapper;

public class InMemoryConsultationQuestionsRepository implements ConsultationQuestionsRepository {

    private final Map<String, ConsultationQuestions> consultationToRequest = new HashMap<>();

    public InMemoryConsultationQuestionsRepository() {}

    @Override
    public ConsultationQuestions getQuestionsForConsultation(String consultationName) {
        var response = consultationToRequest.computeIfAbsent(consultationName, this::createResponseForConsultation);
        consultationToRequest.putIfAbsent(consultationName, response);
        return consultationToRequest.get(consultationName);
    }

    private ConsultationQuestions createResponseForConsultation(String consultationName) {
        final String resourcePath = String.format("consultation-questions/%s.json", consultationName);
        var jsonText = FileUtils.readFileFromResources(resourcePath);
        return jsonText != null
                ? JsonMapper.fromString(jsonText, ConsultationQuestions.class)
                : ConsultationQuestions.NOT_FOUND;
    }
}
