package com.kasumov.med_ci.service.dto.user;

import com.kasumov.med_ci.model.dto.user.patient.PatientDocsDto;
import com.kasumov.med_ci.model.entity.user.Patient;

public interface DocumentsDtoService {
    PatientDocsDto getPatientDocs(Patient patient);
}
