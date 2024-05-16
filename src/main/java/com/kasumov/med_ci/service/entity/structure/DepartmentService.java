package com.kasumov.med_ci.service.entity.structure;

import com.kasumov.med_ci.model.entity.structure.Department;
import com.kasumov.med_ci.model.entity.user.Doctor;
import com.kasumov.med_ci.model.enums.AgeType;

import java.util.List;

public interface DepartmentService {
    List<Department> findMedicalDepartments();

    Boolean isMedicalDepartmentExist(Long departmentId);

    void delete(Department department);

    Department findById(Long departmentId);

    List<Department> getMedicalDepartmentsByParameters(AgeType ageType, String pattern);

    Department save(Department department);

    boolean existsById(long departmentId);

    Doctor getChiefDoctorByDepartmentId(long departmentId);

    Doctor getIOChiefDoctorByDepartmentId(long departmentId);

    Department getInformationByDepartment(long departmentId);

    boolean getDepartmentById(long departmentId);

     Department getDepartmentBysId(Long departmentId);
}

