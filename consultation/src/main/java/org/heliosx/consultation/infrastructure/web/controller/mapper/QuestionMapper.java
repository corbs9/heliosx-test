package org.heliosx.consultation.infrastructure.web.controller.mapper;

import org.heliosx.consultation.domain.model.Question;
import org.heliosx.consultation.infrastructure.web.dto.QuestionDTO;

public class QuestionMapper {
    private QuestionMapper() {}

    public static QuestionDTO fromDomain(Question question) {
        return new QuestionDTO(
                question.id(), question.name(), question.questionType().name());
    }
}
