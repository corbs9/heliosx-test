package org.heliosx.consultation.infrastructure.web.controller.mapper;

import java.util.Collection;
import org.heliosx.consultation.domain.model.AnalyseQuestion;
import org.heliosx.consultation.domain.model.AnalyseQuestionGroup;
import org.heliosx.consultation.infrastructure.web.dto.AnalyseQuestionDTO;
import org.heliosx.consultation.infrastructure.web.dto.AnalyseQuestionGroupDTO;

public class AnalyseQuestionGroupDTOMapper {
    public static AnalyseQuestionGroup toDomain(AnalyseQuestionGroupDTO dto) {
        return new AnalyseQuestionGroup(dto.id(), mapQuestions(dto.questions()));
    }

    private static Collection<AnalyseQuestion> mapQuestions(Collection<AnalyseQuestionDTO> questions) {
        return questions.stream().map(AnalyseQuestionDtoMapper::toDomain).toList();
    }
}
