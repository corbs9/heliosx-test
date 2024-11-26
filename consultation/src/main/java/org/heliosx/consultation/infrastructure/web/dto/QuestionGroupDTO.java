package org.heliosx.consultation.infrastructure.web.dto;

import java.util.Collection;

public record QuestionGroupDTO(long id, String name, Collection<QuestionDTO> questions) {}
