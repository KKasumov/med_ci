package com.kasumov.med_ci.repository.user;

import com.kasumov.med_ci.model.entity.user.items.Polis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import org.springframework.data.repository.query.Param;

public interface PolisRepository extends JpaRepository<Polis, Long> {

    @Query("""
        SELECT p
        FROM Polis p
            JOIN FETCH p.smo
            JOIN p.patientHistory AS h
            JOIN h.patient AS u
        WHERE u.id = :patientID
        ORDER BY p.id
        """)
    List<Polis> getPolisByPatientIdWithSmo(@Param("patientID") long patientId);
}
