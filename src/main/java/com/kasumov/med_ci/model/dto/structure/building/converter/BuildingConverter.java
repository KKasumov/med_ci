package com.kasumov.med_ci.model.dto.structure.building.converter;

import com.kasumov.med_ci.model.dto.structure.building.BuildingDtoCabinet;
import com.kasumov.med_ci.model.entity.structure.Building;
import com.kasumov.med_ci.model.entity.structure.Cabinet;
import com.kasumov.med_ci.model.dto.structure.cabinet.CabinetDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class BuildingConverter {

     public BuildingDtoCabinet convertBuildingToBuildingDTO(Building building, Set<Cabinet> Cabinet) {
        return BuildingDtoCabinet.builder()
                .id(building.getId())
                .physicalAddress(building.getPhysicalAddress())
                .cabinets(Cabinet.stream()
                        .sorted((x, y) -> (int) (x.getId() - y.getId()))
                        .map(newCabinetDtos ->CabinetDto.builder()
                                .id(newCabinetDtos.getId())
                                .name(newCabinetDtos.getName())
                                .number(newCabinetDtos.getNumber())
                                .build())
                        .toList())
                .build();
    }
}
