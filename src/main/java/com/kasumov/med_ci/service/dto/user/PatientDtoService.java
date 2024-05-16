package com.kasumov.med_ci.service.dto.user;

import com.kasumov.med_ci.model.dto.user.patient.CurrentPatientDto;
import com.kasumov.med_ci.model.dto.user.patient.NewPatientDto;
import com.kasumov.med_ci.model.dto.user.patient.PatientDto;
import com.kasumov.med_ci.model.entity.user.Patient;
import com.kasumov.med_ci.model.enums.Gender;

import java.util.List;

public interface PatientDtoService {

    CurrentPatientDto getCurrentPatientDto(Patient patient);

    PatientDto setPatientIsEnabled(Long patientId, boolean isDisabled);

    List<PatientDto> getPatientByParameters(String firstName,
                                            String lastName,
                                            Gender gender,
                                            String snils,
                                            String polisNumber);

    PatientDto saveNewPatient(NewPatientDto newPatientDto);
}