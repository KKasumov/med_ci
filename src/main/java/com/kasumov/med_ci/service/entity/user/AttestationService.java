package com.kasumov.med_ci.service.entity.user;

import com.kasumov.med_ci.model.entity.user.items.Attestation;

import java.util.Set;

public interface AttestationService {
    void deleteById(long id);

    boolean existsById(long id);

    Set<Attestation> getAttestationByDoctorId (long doctorId);

    Attestation saveAttestation(Attestation attestation);

    boolean existsByNumber(String number);
}
