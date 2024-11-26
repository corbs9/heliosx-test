package org.heliosx.consultation.infrastructure.repository;

import java.util.HashMap;
import java.util.Map;
import org.heliosx.consultation.domain.model.ErrorLevel;
import org.heliosx.consultation.domain.model.rules.ConsultationRules;
import org.heliosx.consultation.domain.model.rules.QuestionRule;
import org.heliosx.consultation.domain.model.rules.YesNoRule;
import org.heliosx.consultation.domain.repository.ConsultationRulesRepository;
import org.heliosx.consultation.infrastructure.repository.dto.ConsultationRulesDTO;
import org.heliosx.consultation.infrastructure.repository.dto.QuestionDTO;
import org.heliosx.consultation.utils.FileUtils;
import org.heliosx.consultation.utils.JsonMapper;

public class InMemoryConsultationRulesRepository implements ConsultationRulesRepository {
    private final Map<String, ConsultationRules> consultationNameToRules = new HashMap<>();

    @Override
    public ConsultationRules getRules(String consultationName) {
        var consultationRulesDTO = consultationNameToRules.computeIfAbsent(
                consultationName, (key) -> retrieveConsultationRulesFor(consultationName));
        consultationNameToRules.putIfAbsent(consultationName, consultationRulesDTO);
        return consultationNameToRules.get(consultationName);
    }

    private ConsultationRules retrieveConsultationRulesFor(String consultationName) {
        var filePath = String.format("consultation-rules/%s.json", consultationName);
        var dto = JsonMapper.fromString(FileUtils.readFileFromResources(filePath), ConsultationRulesDTO.class);
        return toConsultationRules(dto);
    }

    private ConsultationRules toConsultationRules(ConsultationRulesDTO rulesDTO) {
        var questionGroups = rulesDTO.questionGroups();
        return new ConsultationRules(questionGroups.stream()
                .flatMap(group -> group.questions().stream()
                        .map(this::transformQuestionDTO)
                        .map(questionIdToRuleMap -> Map.of(group.id(), questionIdToRuleMap)))
                .findFirst()
                .get());
    }

    private Map<Long, QuestionRule> transformQuestionDTO(QuestionDTO q) {
        var rule = q.rule();
        QuestionRule questionRule =
                new YesNoRule(rule.expectedResponse(), ErrorLevel.valueOf(rule.errorLevel()), rule.errorMessage());
        return Map.of(q.id(), questionRule);
    }
}
