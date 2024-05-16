package com.kasumov.med_ci.service.entity.user.impl;

import com.kasumov.med_ci.model.dto.user.employee.EmployeeForHiringDTO;
import com.kasumov.med_ci.model.dto.user.employee.converter.EmployeeDtoConverter;
import com.kasumov.med_ci.repository.user.EmployeeRepository;
import com.kasumov.med_ci.model.entity.user.Employee;
import com.kasumov.med_ci.model.entity.user.Role;
import com.kasumov.med_ci.service.entity.user.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeDtoConverter employeeDtoConverter;

    @Override
    public Employee createEmployee(Role role, EmployeeForHiringDTO dto) {
        Employee employee = employeeDtoConverter.convertToEntity(dto);
        employee.setRole(role);
        return employeeRepository.save(employee);
    }
}
