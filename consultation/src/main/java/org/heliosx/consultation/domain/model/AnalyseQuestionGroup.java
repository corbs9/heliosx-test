package org.heliosx.consultation.domain.model;

import java.util.Collection;

public record AnalyseQuestionGroup(long id, Collection<AnalyseQuestion> questions) {}
