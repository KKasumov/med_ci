package com.kasumov.med_ci.model.dto.user.employee;

import com.kasumov.med_ci.model.dto.user.user.UserDto;
import lombok.Builder;

import java.util.List;

@Builder
public record EmployeeInformationDto(List<Long> employeeHistoryId,
                                     List<Long> laborContractId,
                                     List<UserDto> user) {
}
