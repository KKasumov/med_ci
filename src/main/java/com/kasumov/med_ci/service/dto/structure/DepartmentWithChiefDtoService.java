package com.kasumov.med_ci.service.dto.structure;

import com.kasumov.med_ci.model.dto.structure.department.DepartmentWithChiefDto;
import com.kasumov.med_ci.model.entity.structure.Department;
import com.kasumov.med_ci.model.entity.user.Doctor;

public interface DepartmentWithChiefDtoService {

    DepartmentWithChiefDto addChiefOfDepartment(Department department, Doctor doctor);

    DepartmentWithChiefDto addIOChiefOfDepartment(Department department, Doctor doctor);

    DepartmentWithChiefDto removersChiefOfDepartment(Department department);
}
