package org.heliosx.consultation.domain.model;

import java.util.Collection;

public record AnalysedQuestionGroup(long id, Collection<AnalysedQuestion> questions) {}
