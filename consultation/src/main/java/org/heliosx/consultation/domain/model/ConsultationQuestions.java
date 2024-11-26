package org.heliosx.consultation.domain.model;

import java.util.Collection;

public record ConsultationQuestions(Collection<QuestionGroup> questionGroups) {
    public static final ConsultationQuestions NOT_FOUND = null;
}
