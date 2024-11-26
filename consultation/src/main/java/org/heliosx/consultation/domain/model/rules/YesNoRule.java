package org.heliosx.consultation.domain.model.rules;

import static org.heliosx.consultation.domain.model.ErrorLevel.*;

import org.heliosx.consultation.domain.model.AnalyseQuestion;
import org.heliosx.consultation.domain.model.AnalysedQuestion;
import org.heliosx.consultation.domain.model.ErrorLevel;

public record YesNoRule(String expectedResponse, ErrorLevel errorLevel, String errorMessage) implements QuestionRule {

    @Override
    public AnalysedQuestion analyseQuestion(AnalyseQuestion question) {
        var responseStatus = expectedResponse.equalsIgnoreCase(question.response()) ? OK : errorLevel;
        var responseMessage = OK == responseStatus ? "" : errorMessage;
        return new AnalysedQuestion(question.id(), responseStatus, responseMessage);
    }
}
