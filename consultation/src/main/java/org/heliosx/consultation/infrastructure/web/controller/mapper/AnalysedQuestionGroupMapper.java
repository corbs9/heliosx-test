package org.heliosx.consultation.infrastructure.web.controller.mapper;

import org.heliosx.consultation.domain.model.AnalysedQuestionGroup;
import org.heliosx.consultation.infrastructure.web.dto.AnalysedQuestionGroupDTO;

public class AnalysedQuestionGroupMapper {
    public static AnalysedQuestionGroupDTO fromDomain(AnalysedQuestionGroup questionGroup) {
        return new AnalysedQuestionGroupDTO(
                questionGroup.id(),
                questionGroup.questions().stream()
                        .map(AnalysedQuestionDTOMapper::fromDomain)
                        .toList());
    }
}
