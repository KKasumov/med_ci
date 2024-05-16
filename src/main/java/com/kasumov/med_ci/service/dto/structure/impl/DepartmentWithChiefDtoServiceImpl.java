package com.kasumov.med_ci.service.dto.structure.impl;

import com.kasumov.med_ci.service.dto.structure.DepartmentWithChiefDtoService;
import com.kasumov.med_ci.service.entity.user.RoleService;
import com.kasumov.med_ci.model.dto.structure.department.DepartmentWithChiefDto;
import com.kasumov.med_ci.model.dto.structure.department.converter.DepartmentWithChiefDtoConverter;
import com.kasumov.med_ci.model.entity.structure.Department;
import com.kasumov.med_ci.model.entity.user.Doctor;
import com.kasumov.med_ci.model.enums.RolesEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DepartmentWithChiefDtoServiceImpl implements DepartmentWithChiefDtoService {
    private final RoleService roleService;
    private final DepartmentWithChiefDtoConverter departmentWithChiefDtoConverter;

    @Override
    @Transactional
    public DepartmentWithChiefDto addChiefOfDepartment(Department department, Doctor doctor) {

        if (department.getChiefDoctor() != null) {
            department.getChiefDoctor().setRole(roleService.getRole(RolesEnum.DOCTOR.name()));
        }
        department.setChiefDoctor(doctor);
        doctor.setRole(roleService.getRole(RolesEnum.CHIEF_DOCTOR.name()));
        if (department.getIoChiefDoctor() != null) {
            if (department.getIoChiefDoctor().getId().equals(doctor.getId())) {
                department.setIoChiefDoctor(null);
            }
        }
        return departmentWithChiefDtoConverter.departmentToDepartmentWithChiefDto(department);
    }

    @Override
    @Transactional
    public DepartmentWithChiefDto addIOChiefOfDepartment(Department department, Doctor doctor) {
        if (department.getIoChiefDoctor() != null) {
            department.getIoChiefDoctor().setRole(roleService.getRole(RolesEnum.DOCTOR.name()));
        }
        department.setIoChiefDoctor(doctor);
        doctor.setRole(roleService.getRole(RolesEnum.CHIEF_DOCTOR.name()));
        if (department.getChiefDoctor() != null) {
            if (department.getChiefDoctor().getId().equals(doctor.getId())) {
                department.setChiefDoctor(null);
            }
        }
        return departmentWithChiefDtoConverter.departmentToDepartmentWithChiefDto(department);
    }

    @Override
    @Transactional
    public DepartmentWithChiefDto removersChiefOfDepartment(Department department) {
        if (department.getChiefDoctor()!= null) {
            department.getChiefDoctor().setRole(roleService.getRole(RolesEnum.DOCTOR.name()));
        }
        department.setChiefDoctor(null);
        return departmentWithChiefDtoConverter.departmentToDepartmentWithChiefDto(department);
    }
}
