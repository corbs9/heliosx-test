package org.heliosx.consultation.infrastructure.web.dto;

import java.util.Collection;

public record ConsultationDTO(Collection<QuestionGroupDTO> questionGroups) {}
