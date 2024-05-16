package com.kasumov.med_ci.service.entity.user.impl;

import com.kasumov.med_ci.model.dto.user.patient.PatientDto;
import com.kasumov.med_ci.model.dto.user.patient.converter.PatientDtoConverter;
import com.kasumov.med_ci.repository.user.PatientRepository;
import com.kasumov.med_ci.service.entity.user.PatientService;
import com.kasumov.med_ci.model.entity.user.Invite;
import com.kasumov.med_ci.model.entity.user.Patient;
import com.kasumov.med_ci.model.enums.Gender;
import com.kasumov.med_ci.model.enums.AgeType;
import com.kasumov.med_ci.service.entity.user.InviteService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final InviteService inviteService;
    private final PasswordEncoder passwordEncoder;
    private final PatientDtoConverter patientDtoConverter;

    @Override
    public Optional<Patient> findPatientById(Long patientId) {
        return patientRepository.findById(patientId);
    }

    @Override
    public Patient getPatientById(Long patientId) {
        return patientRepository.findById(patientId).get();
    }

    @Override
    public boolean existsById(long patientId) {
        return patientRepository.existsById(patientId);
    }

    @Override
    public Patient save(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public List<Patient> getAll() {
        return patientRepository.findAll();
    }

    @Override
    public List<Patient> getPatientByParameters(String firstName,
                                                String lastName,
                                                Gender gender,
                                                String snils,
                                                String polisNumber) {
        String parameterGender = gender == null ? null : gender.name();
        return patientRepository.findPatientByParameters(firstName, lastName, parameterGender, snils, polisNumber);
    }

    @Override
    public PatientDto setPasswordGetFromUserAndDeleteInvite(Invite invite, String password) {

        Patient patient = patientRepository.findByEmail(invite.getEmail());
        patient.setPassword(passwordEncoder.encode(password));
        patient = patientRepository.save(patient);
        inviteService.delete(invite);
        return patientDtoConverter.entityToPatientDto(patient);
    }

    @Override
    public AgeType getAgeType(Patient patient) {
        long yearsOld = ChronoUnit.YEARS.between(patient.getBirthday(), LocalDate.now());
        return yearsOld < 18 ? AgeType.CHILD : AgeType.ADULT;
    }

    @Override
    public Patient getPatientByEmail(String email) {
        return patientRepository.findByEmail(email);
    }

}