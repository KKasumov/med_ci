package com.kasumov.med_ci.service.entity.structure;

import com.kasumov.med_ci.model.entity.structure.Wage;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WageService {

    Wage save(Wage wage);

    void updateWageValue(Wage wage);

    Optional<Wage> findById(Long id);

    List<Long> findWagesIdByPositionAndDate(Long positionId, LocalDate startDate, LocalDate endDate);
}
