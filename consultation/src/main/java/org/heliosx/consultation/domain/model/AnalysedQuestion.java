package org.heliosx.consultation.domain.model;

public record AnalysedQuestion(long id, ErrorLevel status, String message) {}
