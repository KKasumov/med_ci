package com.kasumov.med_ci.service.entity.structure.impl;

import com.kasumov.med_ci.repository.structure.WageRepository;
import com.kasumov.med_ci.model.entity.structure.Wage;
import com.kasumov.med_ci.service.entity.structure.WageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WageServiceImpl implements WageService {

    private final WageRepository wageRepository;

    @Override
    public Wage save(Wage wage) {
        return wageRepository.save(wage);
    }

    @Override
    @Transactional
    public void updateWageValue(Wage wage) {
        wageRepository.save(wage);
    }

    @Override
    public Optional<Wage> findById(Long id) {
        return wageRepository.findById(id);
    }

    @Override
    public List<Long> findWagesIdByPositionAndDate(Long positionId, LocalDate startDate, LocalDate endDate) {
        return wageRepository.findWagesIdByPositionAndDate(positionId, startDate, endDate);
    }
}
