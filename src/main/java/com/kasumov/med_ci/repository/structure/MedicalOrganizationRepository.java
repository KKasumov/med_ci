package com.kasumov.med_ci.repository.structure;

import com.kasumov.med_ci.model.entity.structure.MedicalOrganization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MedicalOrganizationRepository extends JpaRepository<MedicalOrganization, Long> {

    @Query("""
            SELECT med
            FROM MedicalOrganization AS med
                JOIN FETCH med.departments
            """)
    MedicalOrganization getOrganizationWithDepartments();

    MedicalOrganization findMedicalOrganizationById(Long organizationId);

    @Query(""" 
            SELECT mo
            FROM MedicalOrganization mo
            """)
    List<MedicalOrganization> getAllMedicalOrganizations();

}

