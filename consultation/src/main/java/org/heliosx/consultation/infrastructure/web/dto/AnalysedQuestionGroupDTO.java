package org.heliosx.consultation.infrastructure.web.dto;

import java.util.Collection;

public record AnalysedQuestionGroupDTO(long id, Collection<AnalysedQuestionDTO> questions) {}
