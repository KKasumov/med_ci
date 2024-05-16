package com.kasumov.med_ci.model.dto.user.university.converter;

import com.kasumov.med_ci.model.dto.user.university.UniversityDto;
import com.kasumov.med_ci.model.dto.user.university.UniversityDtoShort;
import com.kasumov.med_ci.model.entity.user.items.University;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniversityDtoConverter {

    public UniversityDto convertUniversityToUniversityDto(University university) {

        return UniversityDto.builder()
                .id(university.getId())
                .name(university.getName())
                .diplomas(university.getDiplomas())
                .attestation(university.getAttestations())
                .build();
    }

    public UniversityDtoShort convertToUniversityDtoShort(University university) {
        return UniversityDtoShort.builder()
                .name(university.getName())
                .build();
    }

    public UniversityDto convertUniversityToUniversityDtoWithoutDiplomas(University university) {

        return UniversityDto.builder()
                .id(university.getId())
                .name(university.getName())
                .attestation(university.getAttestations())
                .build();
    }

    public UniversityDto convertUniversityToUniversityDtoWithoutDiplomasAndAttestation(University university) {
        return UniversityDto.builder()
                .id(university.getId())
                .name(university.getName())
                .build();
    }


}