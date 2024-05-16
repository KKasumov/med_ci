package com.kasumov.med_ci.repository.medical;

import com.kasumov.med_ci.model.entity.medical.Disease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DiseaseRepository extends JpaRepository<Disease, Long> {

    @Query(value = """
                   SELECT d
                   FROM Disease d
                       LEFT JOIN FETCH d.department
                   WHERE d.id = :diseaseId
                   """)
    Disease findByIdWithDepartment(long diseaseId);

    @Query("""
            SELECT d
            FROM Disease d
                LEFT JOIN FETCH d.department
            WHERE (:departmentId IS NULL OR d.department.id = :departmentId)
                AND (d.name LIKE CONCAT('%', :pattern, '%') OR d.identifier LIKE CONCAT('%', :pattern, '%'))
            """)
    List<Disease> getDiseaseByParameters(String pattern, Long departmentId);


}
