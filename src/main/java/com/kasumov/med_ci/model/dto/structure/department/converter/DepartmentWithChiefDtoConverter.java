package com.kasumov.med_ci.model.dto.structure.department.converter;

import com.kasumov.med_ci.model.dto.structure.department.DepartmentWithChiefDto;
import com.kasumov.med_ci.model.dto.user.user.UserDto;
import com.kasumov.med_ci.model.entity.structure.Department;
import com.kasumov.med_ci.model.entity.user.Doctor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DepartmentWithChiefDtoConverter {


    public DepartmentWithChiefDto departmentToDepartmentWithChiefDto(Department department) {
        return DepartmentWithChiefDto.builder()
                .id(department.getId())
                .name(department.getName())
                .ageType(department.getAgeType())
                .chiefDoctor(department.getChiefDoctor() == null ? null : doctorToUserDto(department.getChiefDoctor()))
                .ioChiefDoctor(department.getIoChiefDoctor() == null ? null : doctorToUserDto(department.getIoChiefDoctor()))
                .build();
    }

    public UserDto doctorToUserDto(Doctor doctor) {
        return UserDto.builder()
                .id(doctor.getId())
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName())
                .patronymic(doctor.getPatronymic())
                .birthday(String.valueOf(doctor.getBirthday()))
                .gender(doctor.getGender())
                .build();
    }
}
