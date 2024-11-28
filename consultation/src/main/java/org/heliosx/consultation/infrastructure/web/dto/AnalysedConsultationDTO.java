package org.heliosx.consultation.infrastructure.web.dto;

import java.util.Collection;
import org.heliosx.consultation.domain.model.ErrorLevel;

public record AnalysedConsultationDTO(ErrorLevel status, Collection<AnalysedQuestionGroupDTO> questionGroups) {}
