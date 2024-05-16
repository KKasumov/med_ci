package com.kasumov.med_ci.model.dto.user.employeeHistory;

import lombok.Builder;
import com.kasumov.med_ci.model.dto.structure.department.DepartmentDto;
import com.kasumov.med_ci.model.dto.user.employee.EmployeeDto;


@Builder
public record EmployeeHistoryDto(Long id,
                                 boolean isPublic,
                                 EmployeeDto employeeDto,
                                 DepartmentDto departmentDto) {
}
