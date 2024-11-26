package org.heliosx.consultation.domain.repository;

import org.heliosx.consultation.domain.model.rules.ConsultationRules;

public interface ConsultationRulesRepository {
    ConsultationRules getRules(String consultationName);
}
