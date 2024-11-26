package org.heliosx.consultation.infrastructure.web.dto;

import java.util.Collection;

public record AnalyseQuestionGroupDTO(long id, Collection<AnalyseQuestionDTO> questions) {}
