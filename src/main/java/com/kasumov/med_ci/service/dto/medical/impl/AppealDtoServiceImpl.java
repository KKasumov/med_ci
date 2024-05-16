package com.kasumov.med_ci.service.dto.medical.impl;

import com.kasumov.med_ci.service.entity.medical.AppealService;
import com.kasumov.med_ci.service.entity.medical.VisitService;
import com.kasumov.med_ci.model.dto.medical.appeal.AppealForPatientNativeFullDto;
import com.kasumov.med_ci.model.dto.medical.appeal.converter.AppealDtoConverter;
import com.kasumov.med_ci.model.dto.medical.visit.VisitMedicalServiceNativeDto;
import com.kasumov.med_ci.model.entity.medical.Appeal;
import com.kasumov.med_ci.model.entity.medical.Visit;
import com.kasumov.med_ci.model.enums.InsuranceType;
import com.kasumov.med_ci.service.dto.medical.AppealDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppealDtoServiceImpl implements AppealDtoService {
    private final AppealService appealService;
    private final VisitService visitService;
    private final AppealDtoConverter appealDtoConverter;

    @Override
    public AppealForPatientNativeFullDto getFullInfoAboutAppeal(Long appealId) {
        Appeal appeal = appealService.findWithVisits(appealId);
        List<VisitMedicalServiceNativeDto> visits = appeal.getVisits().stream()
                .map(Visit::getId)
                .map(visitId -> getPriceByInsuranceType(appeal, visitId))
                .toList();
        return appealDtoConverter.appealToAppealForPatientFullDto(appeal, visits);

    }

    private VisitMedicalServiceNativeDto getPriceByInsuranceType(Appeal appeal, long visitId) {
        return appeal.getInsuranceType().name().equals(InsuranceType.DMS.name()) ?
                visitService.findWithMedicalServicesForDms(visitId) :
                visitService.findWithMedicalServicesForOms(visitId);
    }

}
