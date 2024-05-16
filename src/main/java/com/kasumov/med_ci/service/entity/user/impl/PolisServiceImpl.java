package com.kasumov.med_ci.service.entity.user.impl;

import com.kasumov.med_ci.repository.user.PolisRepository;
import com.kasumov.med_ci.model.entity.user.items.Polis;
import com.kasumov.med_ci.service.entity.user.PolisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PolisServiceImpl implements PolisService {
    private final PolisRepository polisRepository;

    @Override
    public List<Polis> getPolisesByPatientId(Long patientId) {
        return polisRepository.getPolisByPatientIdWithSmo(patientId);
    }

    @Override
    public Polis save(Polis polis) {
        return polisRepository.save(polis);
    }

    @Override
    public List<Polis> getAllPatientPolis(long id) {
        return polisRepository.getPolisByPatientIdWithSmo(id);
    }
}
