package com.kasumov.med_ci.service.entity.user.impl;

import com.kasumov.med_ci.model.dto.user.doctorHistory.converter.DoctorHistoryDtoConverter;
import com.kasumov.med_ci.repository.user.DoctorHistoryRepository;
import com.kasumov.med_ci.model.entity.structure.Position;
import com.kasumov.med_ci.model.entity.user.Employee;
import com.kasumov.med_ci.model.entity.user.Role;
import com.kasumov.med_ci.model.entity.user.history.DoctorHistory;
import com.kasumov.med_ci.service.entity.user.DoctorHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorHistoryServiceImpl implements DoctorHistoryService {

    private final DoctorHistoryRepository doctorHistoryRepository;

    private final DoctorHistoryDtoConverter doctorHistoryDtoConverter;

    @Override
    public DoctorHistory saveDoctorHistory(Role role,
                                           Position position,
                                           Employee employee) {
        return doctorHistoryRepository.save(doctorHistoryDtoConverter.convertToEntity(role, position, employee));
    }
}
