package org.heliosx.consultation.application;

import static org.heliosx.consultation.domain.model.ConsultationQuestions.NOT_FOUND;

import org.heliosx.consultation.GetConsultation;
import org.heliosx.consultation.domain.exception.ConsultationNotFoundException;
import org.heliosx.consultation.domain.model.ConsultationQuestions;
import org.heliosx.consultation.domain.repository.ConsultationQuestionsRepository;

public class GetConsultationService implements GetConsultation {

    private final ConsultationQuestionsRepository repo;

    public GetConsultationService(ConsultationQuestionsRepository repo) {
        this.repo = repo;
    }

    @Override
    public ConsultationQuestions perform(String consultationName) {
        var response = repo.getQuestionsForConsultation(consultationName);
        if (response == NOT_FOUND) throw new ConsultationNotFoundException();
        return response;
    }
}
