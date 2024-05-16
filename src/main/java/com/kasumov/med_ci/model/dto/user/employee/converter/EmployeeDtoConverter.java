package com.kasumov.med_ci.model.dto.user.employee.converter;

import com.kasumov.med_ci.model.dto.user.employeeHistory.converter.EmployeeHistoryDtoConverter;
import com.kasumov.med_ci.config.security.PasswordEncoderConfig;
import com.kasumov.med_ci.model.dto.user.employee.EmployeeForHiringDTO;
import com.kasumov.med_ci.model.dto.user.employee.EmployeeInformationDto;
import com.kasumov.med_ci.model.entity.structure.Position;
import com.kasumov.med_ci.model.entity.user.Employee;
import com.kasumov.med_ci.model.entity.user.items.LaborContract;
import com.kasumov.med_ci.util.DateConvertor;
import com.kasumov.med_ci.util.Generator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class EmployeeDtoConverter {

    private final EmployeeHistoryDtoConverter employeeHistoryDtoConverter;
    private final PasswordEncoderConfig passwordEncoder;
    private final Generator generator;
    private final DateConvertor dateConvertor;

    public EmployeeInformationDto convertEmployeeHistorySetToEmployeeInfDto(Position position) {
        return EmployeeInformationDto.builder()
                .employeeHistoryId(position.getLaborContracts().stream().map(laborContract ->
                        laborContract.getEmployeeHistory().getId()).toList())
                .laborContractId(position.getLaborContracts().stream().map(LaborContract::getId).toList())
                .user(employeeHistoryDtoConverter.convertEmployeeToUserList(position.getLaborContracts()
                                .stream()
                                .map(laborContract ->laborContract.getEmployeeHistory().getEmployee()).toList()))
                .build();
    }

    public Employee convertToEntity(EmployeeForHiringDTO dto) {
        Employee employee = new Employee();
        employee.setEmail(dto.email());
        employee.setSnils(dto.snils());
        employee.setPassword(passwordEncoder.passwordEncoder()
                .encode(generator.getRandomString(8)));
        employee.setFirstName(dto.identityDocumentDto().firstName());
        employee.setLastName(dto.identityDocumentDto().lastName());
        employee.setPatronymic(dto.identityDocumentDto().patronymic());
        employee.setBirthday(dateConvertor.stringToLocalDate(dto.identityDocumentDto().birthday()));
        employee.setGender(dto.identityDocumentDto().gender());
        employee.setEnabled(true);
        return employee;
    }
}
