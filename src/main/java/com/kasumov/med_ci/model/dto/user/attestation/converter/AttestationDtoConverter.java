package com.kasumov.med_ci.model.dto.user.attestation.converter;

import com.kasumov.med_ci.model.dto.user.laborContract.converter.LaborContractDtoConverter;
import com.kasumov.med_ci.model.dto.user.attestation.AttestationDtoWithUniversityAndContract;
import com.kasumov.med_ci.model.dto.user.university.converter.UniversityDtoConverter;
import com.kasumov.med_ci.model.entity.user.items.Attestation;
import com.kasumov.med_ci.util.DateConvertor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AttestationDtoConverter {

    private final UniversityDtoConverter universityDtoConverter;
    private final LaborContractDtoConverter laborContractDtoConverter;

    private final DateConvertor dateConvertor;

    public AttestationDtoWithUniversityAndContract convertAttestationToAttestationDtoWithUniversityAndContract
            (Attestation attestation) {
        return AttestationDtoWithUniversityAndContract.builder()
                .id(attestation.getId())
                .dateFrom(dateConvertor.localDateToString(attestation.getDateFrom()))
                .dateTo(dateConvertor.localDateToString(attestation.getDateTo()))
                .number(attestation.getNumber())
                .universityDto(
                        universityDtoConverter.convertUniversityToUniversityDtoWithoutDiplomasAndAttestation(
                                attestation.getUniversity()))
                .laborContractDto(laborContractDtoConverter.
                        convertLaborContractToDtoWithoutDiplomaAndAttestation(attestation.getLaborContract()))
                .build();
    }
}
