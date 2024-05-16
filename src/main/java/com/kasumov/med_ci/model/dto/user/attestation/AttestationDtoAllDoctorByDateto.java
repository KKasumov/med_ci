package com.kasumov.med_ci.model.dto.user.attestation;

import com.kasumov.med_ci.model.dto.structure.department.DepartmentDto;
import com.kasumov.med_ci.model.dto.user.doctor.DoctorDto;
import lombok.Builder;

import java.util.List;

@Builder
public record AttestationDtoAllDoctorByDateto(List<DoctorDto> doctorDto,
                                              DepartmentDto departmentDto
                                              ) {
}
