package com.kasumov.med_ci.service.dto.structure;


import com.kasumov.med_ci.model.dto.structure.wage.WageDto;
import com.kasumov.med_ci.model.entity.structure.Wage;

import java.time.LocalDate;

public interface WageDtoService {
    WageDto updateWageOfPosition(Wage wage, WageDto wageDto);

    LocalDate getLocalDateByString(String date);

}
