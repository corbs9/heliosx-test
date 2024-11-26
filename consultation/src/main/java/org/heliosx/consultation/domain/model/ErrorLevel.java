package org.heliosx.consultation.domain.model;

public enum ErrorLevel {
    OK(2),
    WARNING(1),
    ERROR(0);

    private final Integer priority;

    ErrorLevel(Integer priority) {
        this.priority = priority;
    }

    public Integer priority() {
        return this.priority;
    }
}
