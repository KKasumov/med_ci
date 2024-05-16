package com.kasumov.med_ci.service.dto.user;

import com.kasumov.med_ci.model.dto.user.employee.EmployeeForHiringDTO;
import com.kasumov.med_ci.model.dto.user.employee.EmployeeOfferDto;
import com.kasumov.med_ci.model.dto.user.hrmanager.CurrentHrManagerDto;
import com.kasumov.med_ci.model.entity.structure.Position;
import com.kasumov.med_ci.model.entity.user.Employee;
import com.kasumov.med_ci.model.entity.user.Role;
import com.kasumov.med_ci.model.entity.user.items.University;

public interface HrManagerDtoService {
    CurrentHrManagerDto getCurrentHrManagerDto(Employee hrManager);

    EmployeeOfferDto hiringEmployee(Role role,
                                    Position position,
                                    University university,
                                    EmployeeForHiringDTO employeeForHiringDTO);
}
