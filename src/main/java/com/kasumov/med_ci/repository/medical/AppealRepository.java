package com.kasumov.med_ci.repository.medical;

import com.kasumov.med_ci.model.entity.medical.Appeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AppealRepository extends JpaRepository<Appeal, Long> {
    boolean existsByDiseaseId(long diseaseId);

    @Query("""
              SELECT ap
              FROM Appeal ap
              JOIN ap.patientHistory ph
              JOIN ph.patient pat
              WHERE ap.id = :appealId             
            """)
    Appeal findWithPatient(long appealId);

    @Query("""
            SELECT ap
                FROM Appeal ap
                JOIN FETCH ap.disease d
                JOIN FETCH ap.visits v
                JOIN FETCH d.department dep
                JOIN FETCH v.doctorHistory dh
                JOIN dh.employee dt           
                WHERE ap.id = :appealId
                AND (d.ageType = dep.ageType OR dep.ageType IS NULL)
                """)
    Appeal findWithVisits(long appealId);
}
