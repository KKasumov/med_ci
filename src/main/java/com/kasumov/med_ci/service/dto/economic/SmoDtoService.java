package com.kasumov.med_ci.service.dto.economic;

import com.kasumov.med_ci.model.dto.economic.smo.SmoDto;
import com.kasumov.med_ci.model.enums.Region;

import java.util.List;

public interface SmoDtoService {
    List<SmoDto> getAllSmo();

    List<SmoDto> getAllSmoDtoByParameters(Region region, String pattern);
}
