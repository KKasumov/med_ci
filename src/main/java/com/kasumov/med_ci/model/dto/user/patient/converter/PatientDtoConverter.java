package com.kasumov.med_ci.model.dto.user.patient.converter;

import com.kasumov.med_ci.model.dto.user.patient.CurrentPatientDto;
import com.kasumov.med_ci.model.dto.user.patient.NewPatientDto;
import com.kasumov.med_ci.model.dto.user.patient.PatientDto;
import com.kasumov.med_ci.model.dto.user.patient.PatientForTalonDto;
import com.kasumov.med_ci.model.entity.user.Patient;
import com.kasumov.med_ci.service.entity.user.RoleService;
import com.kasumov.med_ci.util.DateConvertor;
import com.kasumov.med_ci.util.Generator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.kasumov.med_ci.model.enums.RolesEnum.PATIENT;

@Component
@RequiredArgsConstructor
public class PatientDtoConverter {

    private final RoleService roleService;
    private final Generator generator;
    private final PasswordEncoder encoder;
    private final DateConvertor dateConvertor;

    public PatientDto entityToPatientDto(Patient entity) {
        return PatientDto.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .snils(entity.getSnils())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .patronymic(entity.getPatronymic())
                .birthday(dateConvertor.localDateToString(entity.getBirthday()))
                .gender(entity.getGender())
                .isEnabled(entity.isEnabled())
                .build();
    }

    public CurrentPatientDto entityToCurrentPatientDto(Patient entity) {
        return CurrentPatientDto.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .patronymic(entity.getPatronymic())
                .birthday(dateConvertor.localDateToString(entity.getBirthday()))
                .gender(entity.getGender())
                .build();
    }

    public PatientForTalonDto entityToPatientForTalonDto(Patient entity) {
        return PatientForTalonDto.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .patronymic(entity.getPatronymic())
                .birthday(dateConvertor.localDateToString(entity.getBirthday()))
                .build();
    }

    public Patient convertPatientFromNewPatientDto(NewPatientDto dto) {
        return Patient.builder()
                .email(dto.email())
                .password(encoder.encode(generator.getRandomString(8)))
                .snils(dto.snils())
                .firstName(dto.identityDocument().firstName())
                .lastName(dto.identityDocument().lastName())
                .patronymic(dto.identityDocument().patronymic())
                .birthday(dateConvertor.stringToLocalDate(dto.identityDocument().birthday()))
                .gender(dto.identityDocument().gender())
                .role(roleService.getRole(PATIENT.toString()))
                .build();
    }

    public List<PatientDto> entityToListPatientDto(List<Patient> patientList) {
        return patientList.stream()
                .map(patient ->
                        PatientDto.builder()
                                .id(patient.getId())
                                .email(patient.getEmail())
                                .snils(patient.getSnils())
                                .firstName(patient.getFirstName())
                                .lastName(patient.getLastName())
                                .patronymic(patient.getPatronymic())
                                .birthday(dateConvertor.localDateToString(patient.getBirthday()))
                                .gender(patient.getGender())
                                .isEnabled(patient.isEnabled())
                                .build())
                .toList();
    }

    public PatientForTalonDto patientToPatientForTalonDto(Patient patient) {
        return PatientForTalonDto.builder()
                .id(patient.getId())
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .patronymic(patient.getPatronymic())
                .birthday(dateConvertor.localDateToString(patient.getBirthday()))
                .build();
    }
}