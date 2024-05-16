package com.kasumov.med_ci.service.entity.structure.impl;

import com.kasumov.med_ci.repository.structure.CabinetRepository;
import com.kasumov.med_ci.model.entity.structure.Cabinet;
import com.kasumov.med_ci.service.entity.structure.CabinetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CabinetServiceImpl implements CabinetService {

    private final CabinetRepository cabinetRepository;

    @Override
    public Cabinet save(Cabinet cabinet) {
       return cabinetRepository.save(cabinet);
    }

    @Override
    public Set<Cabinet> saveAllCabinets(Set<Cabinet> cabinetList) {
        return cabinetList == null ? null : new HashSet<>(cabinetRepository.saveAll(cabinetList));
    }

    @Override
    public Cabinet findCabinetById(Long cabinetId) {
        return cabinetRepository.findCabinetById(cabinetId);
    }

    @Override
    public Cabinet getCabinetWithBuildingById(Long cabinetId) {
        return cabinetRepository.getCabinetWithBuildingById(cabinetId);
    }

    @Override
    public boolean existsById(long cabinetId) {
        return cabinetRepository.existsById(cabinetId);
    }

    @Override
    public Optional<Cabinet> getCabinetById(Long id) {
        return cabinetRepository.findById(id);
    }
}
