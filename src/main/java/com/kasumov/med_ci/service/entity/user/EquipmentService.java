package com.kasumov.med_ci.service.entity.user;

import com.kasumov.med_ci.model.entity.user.items.Equipment;

import java.util.List;

public interface EquipmentService {

    Equipment getEquipmentById(Long equipmentId);
    boolean isExistCountFreeEquipmentById(List<Long> equipmentsId);

    boolean isExistsEquipmentsById(List<Long> equipmentsId);

    Equipment getEquipmentByListId(List<Long> equipmentsId);


}
