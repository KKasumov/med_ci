package com.kasumov.med_ci.service.dto.structure.impl;

import com.kasumov.med_ci.service.dto.structure.BuildingDtoService;
import com.kasumov.med_ci.service.entity.structure.BuildingService;
import com.kasumov.med_ci.service.entity.structure.CabinetService;
import com.kasumov.med_ci.service.entity.structure.MedicalOrganizationService;
import com.kasumov.med_ci.model.dto.structure.building.BuildingDto;
import com.kasumov.med_ci.model.dto.structure.building.NewBuildingDto;
import com.kasumov.med_ci.model.dto.structure.building.converter.BuildingDtoConverter;
import com.kasumov.med_ci.model.dto.structure.cabinet.CabinetDto;
import com.kasumov.med_ci.model.entity.structure.Building;
import com.kasumov.med_ci.model.dto.structure.cabinet.converter.CabinetDtoConverter;
import com.kasumov.med_ci.model.entity.structure.Cabinet;
import com.kasumov.med_ci.service.dto.structure.CabinetDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BuildingDtoServiceImpl implements BuildingDtoService {
    private final BuildingService buildingService;
    private final BuildingDtoConverter buildingDtoConverter;

    private final MedicalOrganizationService medicalOrganizationService;
    private final CabinetDtoConverter cabinetDtoConverter;
    private final CabinetService cabinetService;
    private final CabinetDtoService cabinetDtoService;


    @Override
    public List<BuildingDto> getAllBuildings() {
        return buildingDtoConverter.convertBuildingListToBuildingDtoList(buildingService.getAllBuildings());
    }

    @Override
    @Transactional
    public BuildingDto deleteCabinetsInBuilding(Building building, List<Long> cabinetsToRemove) {
        cabinetsToRemove.forEach(building.getCabinets()::remove);
        return buildingDtoConverter.convertBuildingToBuildingDto(building);
    }

    @Override
    @Transactional
    public BuildingDto saveBuilding(NewBuildingDto newBuildingDto) {
        Building building = buildingService.saveBuilding((
                Building.builder()
                        .physicalAddress(newBuildingDto.physicalAddress())
                        .medicalOrganization(medicalOrganizationService.findById(newBuildingDto.
                                medicalOrganizationId()))
                        .build()));
        Set<Cabinet> cabinetSet = cabinetService.saveAllCabinets(
                cabinetDtoConverter.convertNewCabinetDtoListToCabinetSet(newBuildingDto.cabinets(), building));
        building.setCabinets(cabinetSet);
        return buildingDtoConverter.convertBuildingToBuildingDto(building);
    }

    @Override
    @Transactional
    public BuildingDto updateBuildingDto(Building building, BuildingDto buildingDto) {
        building.setPhysicalAddress(buildingDto.physicalAddress());
        List<CabinetDto> cabinetList = buildingDto.cabinets();
        if (cabinetList != null) {
            for (CabinetDto cabinetDto : cabinetList) {
                Cabinet cabinet = cabinetService.getCabinetWithBuildingById(cabinetDto.id());
                if (cabinet != null) {
                    cabinetDtoService.updateCabinetsBuildingDto(cabinet, cabinetDto);
                }
            }
        }
        return buildingDtoConverter.convertBuildingToBuildingDto(building);
    }
}
