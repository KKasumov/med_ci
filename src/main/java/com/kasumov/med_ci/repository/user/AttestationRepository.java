package com.kasumov.med_ci.repository.user;

import com.kasumov.med_ci.model.entity.user.items.Attestation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface AttestationRepository extends JpaRepository<Attestation, Long> {
    @Query("""
            SELECT att from Attestation att
                join att.laborContract lc
                join lc.employeeHistory em
                join em.employee e
                where e.id=:doctorId
            """)
    Set<Attestation> getAttestationByDoctorId(long doctorId);
    boolean existsByNumber(String number);
}
