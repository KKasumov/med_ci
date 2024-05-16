package com.kasumov.med_ci.service.entity.economic.impl;

import com.kasumov.med_ci.model.entity.economic.Smo;
import com.kasumov.med_ci.model.enums.Region;
import com.kasumov.med_ci.repository.economic.SmoRepository;
import com.kasumov.med_ci.service.entity.economic.SmoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SmoServiceImpl implements SmoService {

    private final SmoRepository smoRepository;

    @Override
    public List<Smo> getAll() {
        return smoRepository.findAll();
    }

    @Override
    public boolean existsById(long smoId) {
        return smoRepository.existsById(smoId);
    }

    @Override
    public void delete(Smo smo) {
        smoRepository.delete(smo);
    }

    @Override
    public Smo getSmoById(long smoId) {
        return smoRepository.getSmoById(smoId);
    }

    @Override
    public List<Smo> getSmoListByParameters(Region region, String pattern) {

        Region smoRegion = region == null ? null : region;
        String concatPattern = pattern == null ? "" : pattern;

        return smoRepository.getSmoListByParameters(smoRegion, concatPattern);
    }
}