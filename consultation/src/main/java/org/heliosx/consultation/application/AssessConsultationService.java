package org.heliosx.consultation.application;

import org.heliosx.consultation.AssessConsultation;
import org.heliosx.consultation.domain.model.AnalyseConsultation;
import org.heliosx.consultation.domain.model.AnalyseConsultationRequest;
import org.heliosx.consultation.domain.model.AnalysedConsultation;
import org.heliosx.consultation.domain.repository.ConsultationRulesRepository;

public class AssessConsultationService implements AssessConsultation {
    private final ConsultationRulesRepository consultationRulesRepository;

    public AssessConsultationService(ConsultationRulesRepository consultationRulesRepository) {
        this.consultationRulesRepository = consultationRulesRepository;
    }

    @Override
    public AnalysedConsultation perform(String consultationName, AnalyseConsultationRequest request) {
        var consultationRules = consultationRulesRepository.getRules(consultationName);

        var analyseConsultation = new AnalyseConsultation(consultationRules, request);

        return analyseConsultation.analyse();
    }
}
