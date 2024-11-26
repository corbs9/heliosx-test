package org.heliosx.consultation.domain.model.rules;

import java.util.Map;

public record ConsultationRules(Map<Long, Map<Long, QuestionRule>> rules) {}
