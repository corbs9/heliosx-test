package org.heliosx.consultation.infrastructure.web.controller.mapper;

import org.heliosx.consultation.domain.model.AnalysedConsultation;
import org.heliosx.consultation.infrastructure.web.dto.AnalysedConsultationDTO;

public class AnalyseConsultationResponseMapper {
    public static AnalysedConsultationDTO fromDomain(AnalysedConsultation resp) {
        return new AnalysedConsultationDTO(resp.questionGroups().stream()
                .map(AnalysedQuestionGroupMapper::fromDomain)
                .toList());
    }
}
