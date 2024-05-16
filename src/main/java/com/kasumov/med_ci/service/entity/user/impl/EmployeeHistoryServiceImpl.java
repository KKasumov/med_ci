package com.kasumov.med_ci.service.entity.user.impl;

import com.kasumov.med_ci.model.dto.user.employeeHistory.converter.EmployeeHistoryDtoConverter;
import com.kasumov.med_ci.repository.user.EmployeeHistoryRepository;
import com.kasumov.med_ci.model.entity.structure.Position;
import com.kasumov.med_ci.model.entity.user.Employee;
import com.kasumov.med_ci.model.entity.user.history.EmployeeHistory;
import com.kasumov.med_ci.service.entity.user.EmployeeHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeHistoryServiceImpl implements EmployeeHistoryService {

    private final EmployeeHistoryRepository employeeHistoryRepository;
    private final EmployeeHistoryDtoConverter employeeHistoryDtoConverter;

    @Override
    public EmployeeHistory saveEmployeeHistory(
                                           Position position,
                                           Employee employee) {
        return employeeHistoryRepository.save(employeeHistoryDtoConverter.convertToEntity(position, employee));
    }
}
