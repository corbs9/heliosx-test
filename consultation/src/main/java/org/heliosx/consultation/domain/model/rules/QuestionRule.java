package org.heliosx.consultation.domain.model.rules;

import org.heliosx.consultation.domain.model.AnalyseQuestion;
import org.heliosx.consultation.domain.model.AnalysedQuestion;

public sealed interface QuestionRule permits YesNoRule {
    AnalysedQuestion analyseQuestion(AnalyseQuestion question);
}
