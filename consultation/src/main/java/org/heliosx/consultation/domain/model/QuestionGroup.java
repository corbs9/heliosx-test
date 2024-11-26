package org.heliosx.consultation.domain.model;

import java.util.Collection;

public record QuestionGroup(long id, String name, Collection<Question> questions) {}
