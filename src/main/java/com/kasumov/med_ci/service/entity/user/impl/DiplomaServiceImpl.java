package com.kasumov.med_ci.service.entity.user.impl;

import com.kasumov.med_ci.model.dto.user.diploma.DiplomaForHiringDto;
import com.kasumov.med_ci.model.dto.user.diploma.converter.DiplomaDtoConverter;
import com.kasumov.med_ci.repository.user.DiplomaRepository;
import com.kasumov.med_ci.model.entity.user.items.Diploma;
import com.kasumov.med_ci.model.entity.user.items.University;
import com.kasumov.med_ci.service.entity.user.DiplomaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class DiplomaServiceImpl implements DiplomaService {

    private final DiplomaRepository diplomaRepository;
    private final DiplomaDtoConverter diplomaDtoConverter;

    @Transactional
    @Override
    public void deleteDiplomaById(long diplomaId) {
        diplomaRepository.deleteDiplomaById(diplomaId);
    }

    @Override
    public boolean existsById(long diplomaId) {
        return diplomaRepository.existsById(diplomaId);
    }

    @Override
    public Diploma addDiploma(DiplomaForHiringDto dto, University university) {
        return diplomaRepository.save(diplomaDtoConverter.convertToEntity(dto, university));
    }

    @Override
    public boolean existsBySerialNumber(String serialNumber) {
        return diplomaRepository.existsBySerialNumber(serialNumber);
    }

    @Override
    public Diploma saveDiploma(Diploma diploma) {
        return diplomaRepository.save(diploma);
    }
}
