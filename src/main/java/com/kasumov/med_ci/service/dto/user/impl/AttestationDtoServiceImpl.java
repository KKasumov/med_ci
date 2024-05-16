package com.kasumov.med_ci.service.dto.user.impl;

import com.kasumov.med_ci.model.dto.user.attestation.AttestationDtoWithUniversityAndContract;
import com.kasumov.med_ci.model.dto.user.attestation.NewAttestationDto;
import com.kasumov.med_ci.model.dto.user.attestation.converter.AttestationDtoConverter;
import com.kasumov.med_ci.service.entity.user.AttestationService;
import com.kasumov.med_ci.service.entity.user.LaborContractService;
import com.kasumov.med_ci.service.entity.user.UniversityService;
import com.kasumov.med_ci.util.DateConvertor;
import com.kasumov.med_ci.model.entity.user.items.Attestation;
import com.kasumov.med_ci.service.dto.user.AttestationDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AttestationDtoServiceImpl implements AttestationDtoService {


    private final AttestationService attestationService;

    private final UniversityService universityService;

    private final LaborContractService laborContractService;

    private final AttestationDtoConverter attestationDtoConverter;

    private final DateConvertor dateConvertor;
    @Override
    @Transactional
    public AttestationDtoWithUniversityAndContract saveAttestationByContract(NewAttestationDto newAttestationDto,
                                                                             long contractId) {
        return attestationDtoConverter.convertAttestationToAttestationDtoWithUniversityAndContract
                (attestationService.saveAttestation(
                Attestation.builder()
                        .dateFrom(dateConvertor.stringToLocalDate(newAttestationDto.dateFrom()))
                        .dateTo(dateConvertor.stringToLocalDate(newAttestationDto.dateTo()))
                        .number(newAttestationDto.number())
                        .university(universityService.findUniversityById(newAttestationDto.universityId()))
                        .laborContract(laborContractService.findLaborContractById(contractId))
                        .build()));
    }
}
