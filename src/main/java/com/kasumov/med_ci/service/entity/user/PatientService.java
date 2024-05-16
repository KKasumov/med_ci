package com.kasumov.med_ci.service.entity.user;

import com.kasumov.med_ci.model.dto.user.patient.PatientDto;
import com.kasumov.med_ci.model.entity.user.Invite;
import com.kasumov.med_ci.model.entity.user.Patient;
import com.kasumov.med_ci.model.enums.Gender;
import com.kasumov.med_ci.model.enums.AgeType;

import java.util.List;
import java.util.Optional;

public interface PatientService {

    Optional<Patient> findPatientById(Long patientId);

    boolean existsById(long patientId);

    List<Patient> getAll();

    List<Patient> getPatientByParameters(String firstName,
                                         String lastName,
                                         Gender gender,
                                         String snils,
                                         String polisNumber);

    Patient save(Patient patient);

    Patient getPatientById(Long id);

    PatientDto setPasswordGetFromUserAndDeleteInvite(Invite invite, String password);

    AgeType getAgeType(Patient patient);

    Patient getPatientByEmail(String email);

}