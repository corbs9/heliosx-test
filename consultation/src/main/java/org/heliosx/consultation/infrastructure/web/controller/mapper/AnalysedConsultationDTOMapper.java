package org.heliosx.consultation.infrastructure.web.controller.mapper;

import org.heliosx.consultation.domain.model.AnalysedConsultation;
import org.heliosx.consultation.infrastructure.web.dto.AnalysedConsultationDTO;

public class AnalysedConsultationDTOMapper {
    public static AnalysedConsultationDTO fromDomain(AnalysedConsultation resp) {
        return new AnalysedConsultationDTO(
                resp.status(),
                resp.questionGroups().stream()
                        .map(AnalysedQuestionGroupMapper::fromDomain)
                        .toList());
    }
}
