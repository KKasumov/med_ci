package com.kasumov.med_ci.repository.medical;

import com.kasumov.med_ci.model.entity.medical.MedicalService;
import com.kasumov.med_ci.model.enums.AgeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MedicalServiceRepository extends JpaRepository<MedicalService, Long> {
    boolean existsByIdentifier(String identifier);

    @Query(value = """
            SELECT ms
            FROM MedicalService ms
                LEFT JOIN FETCH ms.department
            WHERE ms.id = :serviceId
            """)
    MedicalService findByIdWithDepartment(long serviceId);

    @Query("""          
            SELECT ms 
            FROM MedicalService ms 
            INNER JOIN  Department dep 
            ON ((dep.ageType != :ageType) and (ms.department.id=dep.id))
            WHERE ((:departmentId IS NULL 
            AND (ms.name LIKE CONCAT('%', :pattern, '%') 
            OR ms.identifier LIKE CONCAT('%', :pattern, '%'))) 
            OR (ms.department.id = :departmentId AND :pattern= ''))          
            """)
    List<MedicalService> getMedicalServiceByDepartmentOrPattern(Long departmentId, String pattern, AgeType ageType);
}