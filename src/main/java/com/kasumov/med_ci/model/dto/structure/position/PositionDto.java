package com.kasumov.med_ci.model.dto.structure.position;

import com.kasumov.med_ci.model.dto.user.employee.EmployeeInformationDto;
import com.kasumov.med_ci.model.dto.structure.cabinet.CabinetWithBuildingDto;
import com.kasumov.med_ci.model.dto.structure.wage.WageDto;
import lombok.Builder;

import javax.annotation.Nullable;


@Builder
public record PositionDto(long id,
        String name,
        int daysForVocation,
        @Nullable CabinetWithBuildingDto cabinet,
        @Nullable EmployeeInformationDto employee,
        @Nullable WageDto wage) {
}
