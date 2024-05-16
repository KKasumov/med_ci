package com.kasumov.med_ci.model.dto.user.employeeHistory.converter;

import com.kasumov.med_ci.model.dto.structure.department.converter.DepartmentDtoConverter;
import com.kasumov.med_ci.model.dto.user.employee.EmployeeDto;
import com.kasumov.med_ci.model.dto.user.employeeHistory.EmployeeHistoryDto;
import com.kasumov.med_ci.model.dto.user.user.UserDto;
import com.kasumov.med_ci.model.entity.structure.Position;
import com.kasumov.med_ci.model.entity.user.Employee;
import com.kasumov.med_ci.model.entity.user.history.EmployeeHistory;
import com.kasumov.med_ci.util.DateConvertor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EmployeeHistoryDtoConverter {

    private final DateConvertor dateConvertor;
    private final DepartmentDtoConverter departmentDtoConverter;

    public List<UserDto> convertEmployeeToUserList(List<Employee> employees) {
        return employees.stream()
                .map(this::convertEmployeeToUserDto)
                .toList();
    }

    private UserDto convertEmployeeToUserDto(Employee employee) {
        return UserDto.builder()
                .id(employee.getId())
                .email(employee.getEmail())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .patronymic(employee.getPatronymic())
                .birthday(dateConvertor.localDateToString(employee.getBirthday()))
                .gender(employee.getGender())
                .build();
    }

    public EmployeeHistoryDto employeeHistoryConvertToDto(EmployeeHistory employeeHistory) {
        return EmployeeHistoryDto
                .builder()
                .id(employeeHistory.getId())
                .employeeDto(
                        EmployeeDto
                                .builder()
                                .id(employeeHistory.getEmployee().getId())
                                .email(employeeHistory.getEmployee().getEmail())
                                .snils(employeeHistory.getEmployee().getSnils())
                                .enabled(employeeHistory.getEmployee().isEnabled())
                                .build()
                )
                .isPublic(employeeHistory.isPublic())
                .departmentDto(departmentDtoConverter
                        .convertDepartmentEntityToDto(employeeHistory.getDepartment()))
                .build();
    }

    public EmployeeHistory convertToEntity(Position position, Employee employee) {
        EmployeeHistory employeeHistory = new EmployeeHistory();
        employeeHistory.setPublic(false);
        employeeHistory.setEmployee(employee);
        employeeHistory.setDepartment(position.getDepartment());
        return employeeHistory;
    }
}
