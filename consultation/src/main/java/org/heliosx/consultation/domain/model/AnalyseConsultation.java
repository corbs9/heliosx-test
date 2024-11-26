package org.heliosx.consultation.domain.model;

import java.util.Collection;
import java.util.Comparator;
import org.heliosx.consultation.domain.model.rules.ConsultationRules;

public record AnalyseConsultation(ConsultationRules consultationRules, AnalyseConsultationRequest request) {
    public AnalysedConsultation analyse() {

        Collection<AnalysedQuestionGroup> analysedQuestionGroups = request.questionGroups().stream()
                .map(group -> new AnalysedQuestionGroup(
                        group.id(),
                        group.questions().stream()
                                .map(question -> consultationRules
                                        .rules()
                                        .get(group.id())
                                        .get(question.id())
                                        .analyseQuestion(question))
                                .toList()))
                .toList();

        ErrorLevel errorLevelForConsultation = analysedQuestionGroups.stream()
                .map(AnalysedQuestionGroup::questions)
                .flatMap(Collection::stream)
                .map(AnalysedQuestion::status)
                .min(Comparator.comparing(ErrorLevel::priority))
                .orElseThrow();

        return new AnalysedConsultation(errorLevelForConsultation, analysedQuestionGroups);
    }
}
