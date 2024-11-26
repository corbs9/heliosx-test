package org.heliosx.consultation.infrastructure.web.controller;

import org.heliosx.consultation.AssessConsultation;
import org.heliosx.consultation.GetConsultation;
import org.heliosx.consultation.domain.model.AnalyseConsultationRequest;
import org.heliosx.consultation.domain.model.AnalysedConsultation;
import org.heliosx.consultation.infrastructure.web.controller.mapper.AnalyseConsultationDTOMapper;
import org.heliosx.consultation.infrastructure.web.controller.mapper.AnalyseConsultationResponseMapper;
import org.heliosx.consultation.infrastructure.web.controller.mapper.ConsultationResponseMapper;
import org.heliosx.consultation.infrastructure.web.dto.AnalyseConsultationDTO;
import org.heliosx.consultation.infrastructure.web.dto.AnalysedConsultationDTO;
import org.heliosx.consultation.infrastructure.web.dto.ConsultationDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultation/{consultationName}")
public class ConsultationController {

    private final GetConsultation getConsultation;
    private final AssessConsultation assessConsultation;

    public ConsultationController(GetConsultation getConsultation, AssessConsultation assessConsultation) {
        this.getConsultation = getConsultation;
        this.assessConsultation = assessConsultation;
    }

    @GetMapping
    ConsultationDTO getConsultation(@PathVariable String consultationName) {
        var consultationQuestions = getConsultation.perform(consultationName);
        return ConsultationResponseMapper.fromDomain(consultationQuestions);
    }

    @PostMapping
    AnalysedConsultationDTO analyseConsultation(
            @PathVariable String consultationName, @RequestBody AnalyseConsultationDTO dto) {
        AnalyseConsultationRequest analysisRequest = AnalyseConsultationDTOMapper.toDomain(dto);
        AnalysedConsultation analysisResponse = assessConsultation.perform(consultationName, analysisRequest);
        return AnalyseConsultationResponseMapper.fromDomain(analysisResponse);
    }
}
