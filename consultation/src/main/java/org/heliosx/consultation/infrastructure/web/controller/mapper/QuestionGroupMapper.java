package org.heliosx.consultation.infrastructure.web.controller.mapper;

import org.heliosx.consultation.domain.model.QuestionGroup;
import org.heliosx.consultation.infrastructure.web.dto.QuestionGroupDTO;

public final class QuestionGroupMapper {
    private QuestionGroupMapper() {}

    public static QuestionGroupDTO fromDomain(QuestionGroup questionGroup) {
        return new QuestionGroupDTO(
                questionGroup.id(),
                questionGroup.name(),
                questionGroup.questions().stream()
                        .map(QuestionMapper::fromDomain)
                        .toList());
    }
}
