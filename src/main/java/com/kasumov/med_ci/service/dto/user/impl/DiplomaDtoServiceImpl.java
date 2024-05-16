package com.kasumov.med_ci.service.dto.user.impl;

import com.kasumov.med_ci.model.dto.user.diploma.DiplomaDtoWithUniversityAndContract;
import com.kasumov.med_ci.model.dto.user.diploma.DiplomaForHiringDto;
import com.kasumov.med_ci.model.dto.user.diploma.converter.DiplomaDtoConverter;
import com.kasumov.med_ci.service.entity.user.DiplomaService;
import com.kasumov.med_ci.service.entity.user.LaborContractService;
import com.kasumov.med_ci.service.entity.user.UniversityService;
import com.kasumov.med_ci.util.DateConvertor;
import com.kasumov.med_ci.model.entity.user.items.Diploma;
import com.kasumov.med_ci.service.dto.user.DiplomaDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DiplomaDtoServiceImpl implements DiplomaDtoService {

    private final DateConvertor dateConvertor;
    private final DiplomaDtoConverter diplomaDtoConverter;
    private final UniversityService universityService;
    private final LaborContractService laborContractService;
    private final DiplomaService diplomaService;

    @Transactional
    @Override
    public DiplomaDtoWithUniversityAndContract saveDiplomaByContract(DiplomaForHiringDto newDiplomaDto, long contractId) {
        return diplomaDtoConverter.convertToDiplomaDtoWithUniversityAndContract
                (diplomaService.saveDiploma(
                        Diploma.builder()
                                .endDate(dateConvertor.stringToLocalDate(newDiplomaDto.endDate()))
                                .serialNumber(newDiplomaDto.serialNumber())
                                .university(universityService.findUniversityById(newDiplomaDto.universityId()))
                                .laborContracts(laborContractService.findLaborContractById(contractId))
                                .build()));
    }
}
