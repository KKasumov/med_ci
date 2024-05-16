package com.kasumov.med_ci.service.dto.user;

import com.kasumov.med_ci.model.dto.user.doctor.CurrentDoctorDto;
import com.kasumov.med_ci.model.entity.user.Doctor;

public interface DoctorDtoService {
    CurrentDoctorDto getCurrentDoctorDto(Doctor doctor);

}
