package org.heliosx.consultation.infrastructure.web.dto;

import org.heliosx.consultation.domain.model.ErrorLevel;

public record AnalysedQuestionDTO(long id, ErrorLevel status, String message) {}
