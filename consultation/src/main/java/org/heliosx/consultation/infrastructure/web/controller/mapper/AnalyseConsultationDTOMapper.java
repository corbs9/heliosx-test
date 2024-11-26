package org.heliosx.consultation.infrastructure.web.controller.mapper;

import java.util.Collection;
import org.heliosx.consultation.domain.model.AnalyseConsultationRequest;
import org.heliosx.consultation.domain.model.AnalyseQuestionGroup;
import org.heliosx.consultation.infrastructure.web.dto.AnalyseConsultationDTO;
import org.heliosx.consultation.infrastructure.web.dto.AnalyseQuestionGroupDTO;

public class AnalyseConsultationDTOMapper {
    public static AnalyseConsultationRequest toDomain(AnalyseConsultationDTO dto) {
        return new AnalyseConsultationRequest(createGroups(dto.questionGroups()));
    }

    private static Collection<AnalyseQuestionGroup> createGroups(Collection<AnalyseQuestionGroupDTO> groups) {
        return groups.stream().map(AnalyseQuestionGroupDTOMapper::toDomain).toList();
    }
}
