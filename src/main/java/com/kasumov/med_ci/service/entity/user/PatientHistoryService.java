package com.kasumov.med_ci.service.entity.user;

import com.kasumov.med_ci.model.entity.user.history.PatientHistory;

public interface PatientHistoryService {

    PatientHistory save(PatientHistory patientHistory);

    PatientHistory findPatientHistoryById(long id);
}
