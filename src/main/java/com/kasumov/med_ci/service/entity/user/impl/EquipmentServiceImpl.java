package com.kasumov.med_ci.service.entity.user.impl;

import com.kasumov.med_ci.repository.user.EquipmentRepository;
import com.kasumov.med_ci.model.entity.user.items.Equipment;
import com.kasumov.med_ci.service.entity.user.EquipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipmentServiceImpl implements EquipmentService {

    private final EquipmentRepository equipmentRepository;

    @Override
    public Equipment getEquipmentById(Long equipmentId) {
        return equipmentRepository.getEquipmentById(equipmentId);
    }

    @Override
    public boolean isExistCountFreeEquipmentById(List<Long> equipmentsId) {
        return equipmentRepository.isExistCountFreeEquipmentById(equipmentsId);
    }

    @Override
    public boolean isExistsEquipmentsById(List<Long> equipmentsId) {
        return equipmentRepository.isExistsEquipmentsById(equipmentsId);
    }

    @Override
    public Equipment getEquipmentByListId(List<Long> equipmentsId) {
        return equipmentRepository.getEquipmentByListId(equipmentsId);
    }

}
