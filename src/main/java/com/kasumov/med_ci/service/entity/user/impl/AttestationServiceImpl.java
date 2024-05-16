package com.kasumov.med_ci.service.entity.user.impl;

import com.kasumov.med_ci.repository.user.AttestationRepository;
import com.kasumov.med_ci.model.entity.user.items.Attestation;
import com.kasumov.med_ci.service.entity.user.AttestationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AttestationServiceImpl implements AttestationService {
    private final AttestationRepository attestationRepository;

    @Override
    public void deleteById(long attestationId) {
        attestationRepository.deleteById(attestationId);
    }

    @Override
    public boolean existsById(long id) {
        return attestationRepository.existsById(id);
    }

    @Override
    public Set<Attestation> getAttestationByDoctorId (long doctorId) {
        return attestationRepository.getAttestationByDoctorId(doctorId);
    }

    @Override
    public Attestation saveAttestation(Attestation attestation) {
        return attestationRepository.save(attestation);
    }

    @Override
    public boolean existsByNumber(String number) {
        return attestationRepository.existsByNumber(number);
    }
}
