package org.heliosx.consultation.infrastructure.web.controller.mapper;

import org.heliosx.consultation.domain.model.ConsultationQuestions;
import org.heliosx.consultation.infrastructure.web.dto.ConsultationDTO;

public final class ConsultationResponseMapper {
    private ConsultationResponseMapper() {}

    public static ConsultationDTO fromDomain(ConsultationQuestions questionResponse) {
        return new ConsultationDTO(questionResponse.questionGroups().stream()
                .map(QuestionGroupMapper::fromDomain)
                .toList());
    }
}
