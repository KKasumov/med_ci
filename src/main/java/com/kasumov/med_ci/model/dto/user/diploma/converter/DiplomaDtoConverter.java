package com.kasumov.med_ci.model.dto.user.diploma.converter;

import com.kasumov.med_ci.model.dto.user.laborContract.converter.LaborContractDtoConverter;
import com.kasumov.med_ci.model.dto.user.university.converter.UniversityDtoConverter;
import com.kasumov.med_ci.model.dto.user.diploma.DiplomaDto;
import com.kasumov.med_ci.model.dto.user.diploma.DiplomaDtoWithUniversityAndContract;
import com.kasumov.med_ci.model.dto.user.diploma.DiplomaForHiringDto;
import com.kasumov.med_ci.model.entity.user.items.Diploma;
import com.kasumov.med_ci.model.entity.user.items.University;
import com.kasumov.med_ci.util.DateConvertor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DiplomaDtoConverter {

    private final DateConvertor dateConvertor;
    private final UniversityDtoConverter universityDtoConverter;
    private final LaborContractDtoConverter laborContractDtoConverter;

    public DiplomaDto convertDiplomaToDiplomaDto(Diploma diploma) {
        return DiplomaDto.builder()
                .id(diploma.getId())
                .serial(diploma.getSerialNumber())
                .endDate(dateConvertor.localDateToString(diploma.getEndDate()))
                .universityDto(universityDtoConverter.convertUniversityToUniversityDtoWithoutDiplomas(diploma.getUniversity()))
                .build();
    }

    public DiplomaDto convertDiplomaToDiplomaDtoWithoutDiplomaAndAttestation(Diploma diploma) {
        return DiplomaDto.builder()
                .id(diploma.getId())
                .serial(diploma.getSerialNumber())
                .endDate(dateConvertor.localDateToString(diploma.getEndDate()))
                .universityDto(universityDtoConverter.convertUniversityToUniversityDtoWithoutDiplomasAndAttestation(
                        diploma.getUniversity()))
                .build();
    }

    public Diploma convertToEntity(DiplomaForHiringDto dto, University university) {
        return Diploma
                .builder()
                .serialNumber(dto.serialNumber())
                .endDate(dateConvertor.stringToLocalDate(dto.endDate()))
                .university(university)
                .build();
    }

    public DiplomaDtoWithUniversityAndContract convertToDiplomaDtoWithUniversityAndContract(Diploma diploma) {
        return DiplomaDtoWithUniversityAndContract.builder()
                .endDate(dateConvertor.localDateToString(diploma.getEndDate()))
                .number(diploma.getSerialNumber())
                .universityDto(
                        universityDtoConverter.convertUniversityToUniversityDtoWithoutDiplomasAndAttestation(
                                diploma.getUniversity()))
                .laborContractDto(laborContractDtoConverter.
                        convertLaborContractToDtoWithoutDiplomaAndAttestation(diploma.getLaborContracts()))
                .build();
    }

}
