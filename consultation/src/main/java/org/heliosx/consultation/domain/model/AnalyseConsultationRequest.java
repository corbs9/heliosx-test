package org.heliosx.consultation.domain.model;

import java.util.Collection;

public record AnalyseConsultationRequest(Collection<AnalyseQuestionGroup> questionGroups) {}
