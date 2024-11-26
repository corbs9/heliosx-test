package org.heliosx.consultation.infrastructure.web.controller.mapper;

import org.heliosx.consultation.domain.model.AnalysedQuestion;
import org.heliosx.consultation.infrastructure.web.dto.AnalysedQuestionDTO;

public class AnalysedQuestionDTOMapper {
    public static AnalysedQuestionDTO fromDomain(AnalysedQuestion question) {
        return new AnalysedQuestionDTO(question.id(), question.status(), question.message());
    }
}
