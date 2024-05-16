package com.kasumov.med_ci.service.entity.structure.impl;

import com.kasumov.med_ci.repository.structure.PositionRepository;
import com.kasumov.med_ci.model.entity.structure.Position;
import com.kasumov.med_ci.service.entity.structure.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PositionServiceImpl implements PositionService {

    private final PositionRepository positionRepository;

    @Override
    public Optional<Position> findById(Long id) {
        return positionRepository.findById(id);
    }

    @Override
    public boolean isExistPositionByCabinet(List<Long> cabinetsId) {
        return positionRepository.isExistPositionByCabinet(cabinetsId);
    }

    @Override
    public List<Position> getPositionByDepartmentId(long departmentId) {
        return positionRepository.getPositionByDepartmentId(departmentId);
    }

    @Override
    public boolean isExistPositionByBuilding(long buildingId) {
        return positionRepository.isExistPositionByBuilding(buildingId);
    }

    @Override
    public Position save(Position position) {
        return positionRepository.save(position);
    }

    @Override
    public Optional<Position> getPositionById(Long positionID) {
        return positionRepository.findPositionById(positionID);
    }
}
