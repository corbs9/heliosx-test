package org.heliosx.consultation;

import org.heliosx.consultation.domain.model.AnalyseConsultationRequest;
import org.heliosx.consultation.domain.model.AnalysedConsultation;

@FunctionalInterface
public interface AssessConsultation {
    AnalysedConsultation perform(String consultationName, AnalyseConsultationRequest request);
}
