package com.kasumov.med_ci.service.dto.user.impl;

import com.kasumov.med_ci.model.dto.user.doctor.CurrentDoctorDto;
import com.kasumov.med_ci.model.dto.user.doctor.converter.DoctorDtoConverter;
import com.kasumov.med_ci.model.entity.user.Doctor;
import com.kasumov.med_ci.service.dto.user.DoctorDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:application.yaml")
public class DoctorDtoServiceImpl implements DoctorDtoService {

    private final DoctorDtoConverter doctorDtoConverter;

    @Override
    public CurrentDoctorDto getCurrentDoctorDto(Doctor doctor) {
        return doctorDtoConverter.entityToCurrentDoctorDto(doctor);
    }
}
