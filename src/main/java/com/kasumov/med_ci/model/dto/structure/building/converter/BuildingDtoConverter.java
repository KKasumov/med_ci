package com.kasumov.med_ci.model.dto.structure.building.converter;

import com.kasumov.med_ci.model.entity.structure.Building;
import com.kasumov.med_ci.model.entity.structure.Cabinet;
import com.kasumov.med_ci.model.dto.structure.building.BuildingDto;
import com.kasumov.med_ci.model.dto.structure.cabinet.CabinetDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BuildingDtoConverter {

    public List<BuildingDto> convertBuildingListToBuildingDtoList(List<Building> buildingList) {
        return buildingList.stream()
                .sorted((x, y) -> (int)(x.getId() - y.getId()))
                .map(this::convertBuildingToBuildingDto)
                .toList();
    }
    public BuildingDto convertBuildingToBuildingDto(Building building) {
        return BuildingDto.builder()
                .id(building.getId())
                .physicalAddress(building.getPhysicalAddress())
                .cabinets(building.getCabinets() == null ? null :
                        convertCabinetListToCabinetDtoList(building.getCabinets().stream().toList()))
                .build();
    }

    public List<CabinetDto> convertCabinetListToCabinetDtoList(List<Cabinet> cabinetList) {
        return cabinetList.stream()
                .sorted((x, y) -> (int)(x.getId() - y.getId()))
                .map(cabinet -> CabinetDto.builder()
                        .id(cabinet.getId())
                        .number(cabinet.getNumber())
                        .name(cabinet.getName())
                        .build())
                .toList();
    }
}
