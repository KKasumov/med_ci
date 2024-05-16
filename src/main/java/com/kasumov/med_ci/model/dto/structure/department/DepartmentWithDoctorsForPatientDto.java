package com.kasumov.med_ci.model.dto.structure.department;

import com.kasumov.med_ci.model.dto.user.doctor.DoctorForPatientDto;
import com.kasumov.med_ci.model.enums.AgeType;
import lombok.Builder;

import java.util.List;

@Builder
public record DepartmentWithDoctorsForPatientDto(long id,
                                                 String name,
                                                 AgeType ageType,
                                                 List<DoctorForPatientDto> doctors) {
}