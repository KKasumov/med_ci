package com.kasumov.med_ci.repository.structure;

import com.kasumov.med_ci.model.entity.structure.License;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LicenseRepository extends JpaRepository<License, Long> {

    @Query("""
            SELECT l FROM License l 
            JOIN FETCH l.medicalOrganization lm
            JOIN FETCH lm.director
            JOIN fetch lm.ioDirector
            """)
    List<License> getAllLicenses();

}
