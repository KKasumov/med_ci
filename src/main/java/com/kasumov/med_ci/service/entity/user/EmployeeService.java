package com.kasumov.med_ci.service.entity.user;

import com.kasumov.med_ci.model.dto.user.employee.EmployeeForHiringDTO;
import com.kasumov.med_ci.model.entity.user.Employee;
import com.kasumov.med_ci.model.entity.user.Role;

public interface EmployeeService {

    Employee createEmployee(Role role, EmployeeForHiringDTO employeeForHiringDTO);
}
