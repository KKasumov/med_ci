package com.kasumov.med_ci.service.entity.structure.impl;

import com.kasumov.med_ci.repository.structure.BuildingRepository;
import com.kasumov.med_ci.model.entity.structure.Building;
import com.kasumov.med_ci.model.entity.structure.Cabinet;
import com.kasumov.med_ci.service.entity.structure.BuildingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BuildingServiceImpl implements BuildingService {
    private final BuildingRepository buildingRepository;

    @Override
    public Building getBuildingById(Long buildingId) {
        return buildingRepository.getBuildingById(buildingId);
    }

    @Override
    public List<Building> getAllBuildings() {
        return buildingRepository.getBuildingListWithCabinets();
    }

    @Override
    public Building getCabinetInBuildingById(Long buildingId, List<Long> cabinetsId) {
        return buildingRepository.getCabinetInBuildingById(buildingId, cabinetsId);
    }

    @Override
    public boolean isExistCabinetInBuilding(Building building, List<Long> cabinetsId) {
        Set<Long> buildingCabinetsId = building.getCabinets().stream()
                .map(Cabinet::getId)
                .collect(Collectors.toSet());
        return buildingCabinetsId.containsAll(cabinetsId);
    }

    @Override
    public Building save(Building building) {
        return buildingRepository.save(building);
    }

    @Override
    public Building saveBuilding(Building building) {
        return buildingRepository.save(building);
    }

    @Override
    public boolean isExistsByPhysicalAddress(String physicalAddress) {
        return buildingRepository.getBuildingByPhysicalAddress(physicalAddress) != null;
    }

    @Override
    @Transactional
    public void removeBuildingById(long id) {
        buildingRepository.deleteById(id);
    }

    @Override
    public boolean isExistsById(long id) {
        return buildingRepository.existsById(id);
    }
}
