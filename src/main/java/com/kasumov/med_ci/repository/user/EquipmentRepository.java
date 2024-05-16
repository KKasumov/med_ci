package com.kasumov.med_ci.repository.user;

import com.kasumov.med_ci.model.entity.user.items.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {

    Equipment getEquipmentById(Long equipmentId);

    @Query("""
           SELECT COUNT(e) > 0 
           FROM Equipment e 
           WHERE e.id = :equipmentsId 
           AND e.position IS NULL 
           AND e.disposalDate IS NULL
""")
    boolean isExistCountFreeEquipmentById(List<Long> equipmentsId);

    @Query("""
            SELECT CASE WHEN COUNT (e)> 0 
            THEN true ELSE false END
            FROM Equipment e
            WHERE e.id IN (:equipmentsId)
            """)
    boolean isExistsEquipmentsById(List<Long> equipmentsId);

    @Query("""
            SELECT e 
            FROM Equipment e 
            WHERE e.id = :equipmentsId
            """)
    Equipment getEquipmentByListId(List<Long> equipmentsId);
}
