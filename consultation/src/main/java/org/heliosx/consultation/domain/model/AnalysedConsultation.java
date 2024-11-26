package org.heliosx.consultation.domain.model;

import java.util.Collection;

public record AnalysedConsultation(ErrorLevel status, Collection<AnalysedQuestionGroup> questionGroups) {}
