package com.kasumov.med_ci.service.entity.user;

import com.kasumov.med_ci.model.entity.structure.Department;
import com.kasumov.med_ci.model.entity.user.Doctor;
import com.kasumov.med_ci.model.entity.user.Patient;

import java.util.List;
import java.util.Optional;

public interface DoctorService {
    List<Doctor> getFreeDoctorsForPatient(Patient patient);

    List<Doctor> getDoctorsWithDepartmentsByAgeType();

    boolean existsById(long doctorId);

    Optional<Doctor> findById(long doctorId);

    boolean isExistDoctorByLaborContractValid(long id);

    Optional<Doctor> getDoctorWithRole(long id);

    Doctor findDoctorByIdWithTalons(long doctorId);

    boolean chiefByDepartment(Department department);

    List<Doctor> getDoctorAllByEndAttestation(Integer day, Long departmentId);

}
