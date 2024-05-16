package com.kasumov.med_ci.service.dto.structure;

import com.kasumov.med_ci.model.dto.structure.position.NewPositionDto;
import com.kasumov.med_ci.model.dto.structure.position.PositionDto;

import java.util.List;


public interface PositionDtoService {

    List<PositionDto> getPositionDtoByDepartmentId(long departmentId);

    PositionDto addNewPosition(long departmentId, NewPositionDto newPositionDto);

}
