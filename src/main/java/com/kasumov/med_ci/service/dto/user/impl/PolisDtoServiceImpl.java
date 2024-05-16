package com.kasumov.med_ci.service.dto.user.impl;

import com.kasumov.med_ci.model.dto.user.polis.NewPolisDto;
import com.kasumov.med_ci.model.dto.user.polis.PolisDto;
import com.kasumov.med_ci.model.dto.user.polis.converter.PolisDtoConverter;
import com.kasumov.med_ci.service.entity.economic.SmoService;
import com.kasumov.med_ci.service.entity.user.PolisService;
import com.kasumov.med_ci.util.DateConvertor;
import com.kasumov.med_ci.model.entity.user.Patient;
import com.kasumov.med_ci.model.entity.user.items.Polis;
import com.kasumov.med_ci.model.enums.InsuranceType;
import com.kasumov.med_ci.service.dto.user.PolisDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PolisDtoServiceImpl implements PolisDtoService {
    private final PolisService polisService;
    private final SmoService smoService;
    private final DateConvertor dateConvertor;
    private final PolisDtoConverter polisDtoConverter;

    @Override
    public List<PolisDto> getAllPatientPolis(Long patientId) {
        return polisService.getAllPatientPolis(patientId).stream()
                .map(polisDtoConverter::convertPolisToPolisDto)
                .toList();
    }

    @Override
    @Transactional
    public List<PolisDto> saveNewPolisByPatient(Patient patient, NewPolisDto newPolisDto) {
        if (newPolisDto.insuranceType() == InsuranceType.OMS) {
            polisService.getAllPatientPolis(patient.getId()).stream()
                    .filter(polis -> polis.getInsuranceType() == InsuranceType.OMS)
                    .forEach(polis -> polis.setDeprecated(true));
        }
        polisService.save(Polis.builder()
                .insuranceType(newPolisDto.insuranceType())
                .serial(newPolisDto.serial())
                .number(newPolisDto.number())
                .dateStart(dateConvertor.stringToLocalDate(newPolisDto.dateStart()))
                .dateEnd(dateConvertor.stringToLocalDate(newPolisDto.dateEnd()))
                .isDeprecated(false)
                .smo(smoService.getSmoById(newPolisDto.smoId()))
                .patientHistory(patient.getPatientHistory())
                .build());
        return polisService.getAllPatientPolis(patient.getId()).stream()
                .map(polisDtoConverter::convertPatientPolisToPolisDto)
                .toList();
    }
}
