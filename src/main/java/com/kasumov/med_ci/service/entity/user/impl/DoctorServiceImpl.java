package com.kasumov.med_ci.service.entity.user.impl;

import com.kasumov.med_ci.repository.user.DoctorRepository;
import com.kasumov.med_ci.model.entity.structure.Department;
import com.kasumov.med_ci.model.entity.user.Doctor;
import com.kasumov.med_ci.model.entity.user.Patient;
import com.kasumov.med_ci.model.enums.AgeType;
import com.kasumov.med_ci.service.entity.user.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private static final int Default_Day = 30;

    @Value("${patientScope}")
    private int patientScope;

    @Override
    public List<Doctor> getFreeDoctorsForPatient(Patient patient) {
        return doctorRepository
                .getFreeDoctorsByAgeTypeAndScope(LocalDateTime.now().plusDays(patientScope), getAgeType(patient));
    }

    @Override
    public List<Doctor> getDoctorsWithDepartmentsByAgeType() {
        return doctorRepository.getDoctorsWithDepartmentsByAgeType(Set.of(AgeType.ADULT, AgeType.CHILD));
    }

    @Override
    public boolean isExistDoctorByLaborContractValid(long doctorId) {
        return doctorRepository.isExistDoctorByLaborContractValid(doctorId);
    }

    @Override
    public Optional<Doctor> getDoctorWithRole(long doctorId) {
        return Optional.ofNullable(doctorRepository.getDoctorWithRole(doctorId));
    }

    private AgeType getAgeType(Patient patient) {
        long yearsOld = ChronoUnit.YEARS.between(patient.getBirthday(), LocalDate.now());
        return yearsOld < 18 ? AgeType.CHILD : AgeType.ADULT;
    }

    @Override
    public boolean existsById(long doctorId) {
        return doctorRepository.existsById(doctorId);
    }

    @Override
    public Optional<Doctor> findById(long doctorId) {
        return doctorRepository.findById(doctorId);
    }

    @Override
    public Doctor findDoctorByIdWithTalons(long doctorId) {
        return doctorRepository.findDoctorByIdWithTalons(doctorId);
    }

    @Override
    public boolean chiefByDepartment(Department department) {
        return department.getChiefDoctor() == null;
    }
    @Override
    public List<Doctor> getDoctorAllByEndAttestation(Integer day, Long departmentId) {
        if (day == null || day == 0) {
            day = Default_Day;
        }
        return departmentId == null ? doctorRepository.getDoctorAllByEndAttestationNoDepartmentId(LocalDate.now(),
                LocalDate.now().plusDays(day)) : doctorRepository.getDoctorAllByEndAttestation(LocalDate.now(),
                LocalDate.now().plusDays(day), departmentId);
    }
}
