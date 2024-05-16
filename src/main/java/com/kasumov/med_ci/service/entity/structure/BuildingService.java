package com.kasumov.med_ci.service.entity.structure;

import com.kasumov.med_ci.model.entity.structure.Building;

import java.util.List;

public interface BuildingService {

    public Building getBuildingById(Long buildingId);
    List<Building> getAllBuildings();

    Building getCabinetInBuildingById(Long buildingId, List<Long> cabinetsId);

    boolean isExistCabinetInBuilding(Building building, List<Long> cabinetsId);

    Building save(Building building);

    Building saveBuilding(Building building);

    boolean isExistsByPhysicalAddress(String physicalAddress);

    void removeBuildingById(long id);

    boolean isExistsById(long id);

}
