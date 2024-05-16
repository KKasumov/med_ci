package com.kasumov.med_ci.model.dto.user.doctorHistory.converter;

import com.kasumov.med_ci.model.entity.structure.Position;
import com.kasumov.med_ci.model.entity.user.Employee;
import com.kasumov.med_ci.model.entity.user.Role;
import com.kasumov.med_ci.model.entity.user.history.DoctorHistory;
import com.kasumov.med_ci.model.enums.RolesEnum;
import org.springframework.stereotype.Component;

@Component
public class DoctorHistoryDtoConverter {

    public DoctorHistory convertToEntity(Role role, Position position, Employee employee) {
        return DoctorHistory.builder()
                .isPublic(role.getName().equals(RolesEnum.DOCTOR.name()))
                .employee(employee)
                .department(position.getDepartment())
                .addTalonAuto(false)
                .build();
    }
}
