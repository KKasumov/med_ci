package com.kasumov.med_ci.model.dto.user.laborContract;

import com.kasumov.med_ci.model.dto.user.employeeHistory.EmployeeHistoryDto;
import com.kasumov.med_ci.model.dto.structure.position.PositionDto;
import com.kasumov.med_ci.model.dto.user.diploma.DiplomaDto;
import lombok.Builder;
import org.springframework.lang.Nullable;
import java.math.BigDecimal;

@Builder
public record LaborContractDto(Long id,
                               String startDate,
                               @Nullable String endDate,
                               BigDecimal part,
                               EmployeeHistoryDto employeeHistoryDto,
                               PositionDto positionDto,
                               DiplomaDto diplomaDto) {
}
