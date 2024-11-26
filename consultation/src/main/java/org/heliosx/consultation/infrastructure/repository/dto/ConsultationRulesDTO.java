package org.heliosx.consultation.infrastructure.repository.dto;

import java.util.Collection;

public record ConsultationRulesDTO(Collection<QuestionGroupDTO> questionGroups) {}
