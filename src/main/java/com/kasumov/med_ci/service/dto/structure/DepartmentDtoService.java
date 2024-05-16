package com.kasumov.med_ci.service.dto.structure;

import com.kasumov.med_ci.model.dto.user.attestation.AttestationDtoAllDoctorByDateto;
import com.kasumov.med_ci.model.dto.structure.department.DepartmentDto;
import com.kasumov.med_ci.model.dto.structure.department.DepartmentWithChiefDto;
import com.kasumov.med_ci.model.dto.structure.department.DepartmentWithDoctorsForPatientDto;
import com.kasumov.med_ci.model.dto.structure.department.DepartmentWithDoctorsForRegistrarDto;
import com.kasumov.med_ci.model.dto.structure.department.DepartmentWithPositionDto;
import com.kasumov.med_ci.model.dto.structure.department.DepartmentWithTalonsDto;
import com.kasumov.med_ci.model.dto.structure.department.NewDepartmentDto;
import com.kasumov.med_ci.model.entity.structure.Department;
import com.kasumov.med_ci.model.entity.structure.MedicalOrganization;
import com.kasumov.med_ci.model.entity.user.Patient;
import com.kasumov.med_ci.model.enums.AgeType;
import org.springframework.lang.Nullable;

import java.util.List;

public interface DepartmentDtoService {
    List<DepartmentWithTalonsDto> getDepartmentWithTalonsOnTodayDto();

    List<DepartmentWithDoctorsForPatientDto> getDepartmentsSuitableForAge(Patient patient);

    List<DepartmentWithDoctorsForRegistrarDto> getDepartmentsAndDoctorsDto();

    DepartmentWithChiefDto updateDepartment(DepartmentDto departmentDto);

    List<DepartmentDto> getMedicalDepartmentsByParameters(@Nullable AgeType ageType, String pattern);

    DepartmentWithChiefDto addNewDepartment(NewDepartmentDto department, MedicalOrganization medicalOrganization);

    DepartmentWithPositionDto getInformationByDepartment(Department department);

    AttestationDtoAllDoctorByDateto getAttestationDtoAllDoctorByDate(Long departmentId,
                                                                     Integer day, Department department);
}
