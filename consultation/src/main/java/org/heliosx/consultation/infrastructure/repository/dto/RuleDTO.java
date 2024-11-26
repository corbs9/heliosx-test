package org.heliosx.consultation.infrastructure.repository.dto;

public record RuleDTO(String type, String expectedResponse, String errorLevel, String errorMessage) {}
