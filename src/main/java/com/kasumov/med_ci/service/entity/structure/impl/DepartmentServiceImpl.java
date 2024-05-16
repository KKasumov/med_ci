package com.kasumov.med_ci.service.entity.structure.impl;

import com.kasumov.med_ci.repository.structure.DepartmentRepository;
import com.kasumov.med_ci.model.entity.structure.Department;
import com.kasumov.med_ci.model.entity.user.Doctor;
import com.kasumov.med_ci.model.enums.AgeType;
import com.kasumov.med_ci.service.entity.structure.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;


    @Override
    public List<Department> findMedicalDepartments() {
        return departmentRepository.findDepartmentsWithChiefByAgeType(Set.of(AgeType.ADULT, AgeType.CHILD));
    }

    @Override
    public boolean existsById(long departmentId) {
        return departmentRepository.existsById(departmentId);
    }


    @Override
    public Doctor getChiefDoctorByDepartmentId(long departmentId) {
        return departmentRepository.getChiefDoctorByDepartmentId(departmentId);
    }

    @Override
    public Department save(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public Boolean isMedicalDepartmentExist(Long departmentId) {
        return departmentRepository.findDepartmentByIdAndAgeType(departmentId,
                Set.of(AgeType.ADULT, AgeType.CHILD)) == null;
    }

    @Override
    public void delete(Department department) {
        departmentRepository.delete(department);
    }

    @Override
    public Department findById(Long departmentId) {
        return departmentRepository.findDepartmentById(departmentId);
    }

    @Override
    public List<Department> getMedicalDepartmentsByParameters(AgeType ageType, String pattern) {
        return departmentRepository.getAllDepartmentsByParameters((ageType == AgeType.NO) || (ageType == null) ?
                Set.of(AgeType.ADULT, AgeType.CHILD) : Set.of(ageType), pattern);
    }

    @Override
    public Doctor getIOChiefDoctorByDepartmentId(long departmentId) {
        return departmentRepository.getIOChiefDoctorByDepartmentId(departmentId);
    }

    @Override
    public Department getInformationByDepartment(long departmentId) {
        return departmentRepository.getInformationByDepartment(departmentId);
    }

    @Override
    public boolean getDepartmentById(long departmentId) {
        return departmentRepository.existsById(departmentId);
    }

    @Override
    public Department getDepartmentBysId(Long departmentId) {
        return departmentId == null ? null : departmentRepository.getDepartmentById(departmentId);
    }
}
