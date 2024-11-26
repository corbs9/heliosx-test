package org.heliosx.consultation.config;

import org.heliosx.consultation.AssessConsultation;
import org.heliosx.consultation.GetConsultation;
import org.heliosx.consultation.application.AssessConsultationService;
import org.heliosx.consultation.application.GetConsultationService;
import org.heliosx.consultation.domain.repository.ConsultationQuestionsRepository;
import org.heliosx.consultation.domain.repository.ConsultationRulesRepository;
import org.heliosx.consultation.infrastructure.repository.InMemoryConsultationQuestionsRepository;
import org.heliosx.consultation.infrastructure.repository.InMemoryConsultationRulesRepository;
import org.heliosx.consultation.infrastructure.web.controller.ConsultationController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsultationConfig {

    @Bean
    ConsultationController consultationController(
            GetConsultation getConsultation, AssessConsultation assessConsultation) {
        return new ConsultationController(getConsultation, assessConsultation);
    }

    @Bean
    GetConsultation getConsultationQuestions(ConsultationQuestionsRepository repo) {
        return new GetConsultationService(repo);
    }

    @Bean
    ConsultationQuestionsRepository consultationQuestionsRepository() {
        return new InMemoryConsultationQuestionsRepository();
    }

    @Bean
    AssessConsultation analyseConsultation(ConsultationRulesRepository repo) {
        return new AssessConsultationService(repo);
    }

    @Bean
    ConsultationRulesRepository consultationRulesRepository() {
        return new InMemoryConsultationRulesRepository();
    }
}
