package org.heliosx.consultation;

import org.heliosx.consultation.domain.model.ConsultationQuestions;

@FunctionalInterface
public interface GetConsultation {
    ConsultationQuestions perform(String consultationName);
}
