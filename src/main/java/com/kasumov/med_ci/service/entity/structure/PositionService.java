package com.kasumov.med_ci.service.entity.structure;

import com.kasumov.med_ci.model.entity.structure.Position;

import java.util.List;
import java.util.Optional;

public interface PositionService {

    Optional<Position> findById(Long id);

    boolean isExistPositionByCabinet(List<Long> cabinetsId);

    List<Position> getPositionByDepartmentId(long departmentId);

    Position save(Position position);

    boolean isExistPositionByBuilding(long buildingId);

    Optional<Position> getPositionById(Long positionID);
}
