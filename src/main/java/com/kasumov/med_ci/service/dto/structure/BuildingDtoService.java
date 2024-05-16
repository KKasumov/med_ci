package com.kasumov.med_ci.service.dto.structure;

import com.kasumov.med_ci.model.dto.structure.building.BuildingDto;
import com.kasumov.med_ci.model.entity.structure.Building;
import com.kasumov.med_ci.model.dto.structure.building.NewBuildingDto;

import java.util.List;

public interface BuildingDtoService {

    List<BuildingDto> getAllBuildings();

    BuildingDto deleteCabinetsInBuilding(Building building, List<Long> cabinetsToRemove);

    BuildingDto saveBuilding(NewBuildingDto newBuildingDto);

    BuildingDto updateBuildingDto(Building building, BuildingDto buildingDto);
}
