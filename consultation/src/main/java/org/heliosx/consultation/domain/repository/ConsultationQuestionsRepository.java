package org.heliosx.consultation.domain.repository;

import org.heliosx.consultation.domain.model.ConsultationQuestions;

public interface ConsultationQuestionsRepository {

    ConsultationQuestions getQuestionsForConsultation(String consultationName);
}
