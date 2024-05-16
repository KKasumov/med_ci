package com.kasumov.med_ci.model.dto.utill.converter;

import com.kasumov.med_ci.model.dto.utill.MessageDto;
import com.kasumov.med_ci.model.entity.medical.Talon;
import com.kasumov.med_ci.model.entity.structure.Department;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageDtoConverter {
    public MessageDto messageForPatientToAssignFreeTalon(Department department, Talon talon) {
        return MessageDto.builder()
                .nameDepartment(department.getName())
                .nameDoctor(talon.getDoctorHistory().getEmployee().getFirstName())
                .surnameDoctor(talon.getDoctorHistory().getEmployee().getLastName())
                .patronymicDoctor(talon.getDoctorHistory().getEmployee().getPatronymic())
                .time(String.valueOf(talon.getTime()))
                .build();
    }
}
