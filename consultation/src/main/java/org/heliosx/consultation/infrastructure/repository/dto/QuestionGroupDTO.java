package org.heliosx.consultation.infrastructure.repository.dto;

import java.util.Collection;

public record QuestionGroupDTO(long id, Collection<QuestionDTO> questions) {}
