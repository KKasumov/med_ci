package com.kasumov.med_ci.model.dto.structure.cabinet.converter;

import com.kasumov.med_ci.model.entity.structure.Building;
import com.kasumov.med_ci.model.entity.structure.Cabinet;
import com.kasumov.med_ci.model.entity.structure.Position;
import com.kasumov.med_ci.model.dto.structure.cabinet.CabinetWithBuildingDto;
import com.kasumov.med_ci.model.dto.structure.cabinet.NewCabinetDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CabinetDtoConverter {

    public Set<Cabinet> convertNewCabinetDtoListToCabinetSet(List<NewCabinetDto> newCabinetDtoList,
                                                             Building building) {
        return newCabinetDtoList == null ? null : newCabinetDtoList.stream()
                .map(cabinet -> Cabinet.builder()
                        .name(cabinet.name())
                        .number(cabinet.number())
                        .building(building)
                        .build())
                .collect(Collectors.toSet());
    }
    public CabinetWithBuildingDto convertCabinetToCabinetWithBuildingDto(Position position) {
        Cabinet cabinet = position.getCabinet();
        return CabinetWithBuildingDto.builder()
                .cabinetId(cabinet.getId())
                .cabinetNumber(cabinet.getNumber())
                .cabinetName(cabinet.getName())
                .buildingId(cabinet.getBuilding().getId())
                .buildingPhysicalAddress(cabinet.getBuilding().getPhysicalAddress())
                .build();
    }


    public CabinetWithBuildingDto cabinetToCabinetWithBuildingDtoConverter(Cabinet cabinet) {
        return CabinetWithBuildingDto.builder()
                .cabinetId(cabinet.getId())
                .cabinetNumber(cabinet.getNumber())
                .cabinetName(cabinet.getName())
                .buildingId(cabinet.getBuilding().getId())
                .buildingPhysicalAddress(cabinet.getBuilding().getPhysicalAddress())
                .build();
    }
}
