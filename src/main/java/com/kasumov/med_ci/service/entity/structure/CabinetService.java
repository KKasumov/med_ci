package com.kasumov.med_ci.service.entity.structure;

import com.kasumov.med_ci.model.entity.structure.Cabinet;

import java.util.Set;
import java.util.Optional;

public interface CabinetService {
    Cabinet save(Cabinet cabinet);

    Set<Cabinet> saveAllCabinets(Set<Cabinet> cabinetList);

//    Cabinet getCabinetDepartmentId(List<Position> position);

    Cabinet findCabinetById(Long cabinetId);

    Cabinet getCabinetWithBuildingById(Long cabinetId);

    boolean existsById(long cabinetId);

    Optional<Cabinet> getCabinetById(Long id);
}
