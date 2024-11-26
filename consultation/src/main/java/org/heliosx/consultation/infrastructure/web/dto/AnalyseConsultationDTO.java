package org.heliosx.consultation.infrastructure.web.dto;

import java.util.Collection;

public record AnalyseConsultationDTO(Collection<AnalyseQuestionGroupDTO> questionGroups) {}
