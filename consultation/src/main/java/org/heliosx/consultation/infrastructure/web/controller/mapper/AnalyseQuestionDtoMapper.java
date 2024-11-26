package org.heliosx.consultation.infrastructure.web.controller.mapper;

import org.heliosx.consultation.domain.model.AnalyseQuestion;
import org.heliosx.consultation.infrastructure.web.dto.AnalyseQuestionDTO;

public class AnalyseQuestionDtoMapper {
    public static AnalyseQuestion toDomain(AnalyseQuestionDTO dto) {
        return new AnalyseQuestion(dto.id(), dto.response());
    }
}
