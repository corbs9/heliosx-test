package org.heliosx.consultation.infrastructure.web.dto;

import java.util.Collection;

public record AnalysedConsultationDTO(Collection<AnalysedQuestionGroupDTO> questionGroups) {}
