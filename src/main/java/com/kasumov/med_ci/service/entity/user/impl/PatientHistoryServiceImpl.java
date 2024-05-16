package com.kasumov.med_ci.service.entity.user.impl;

import com.kasumov.med_ci.repository.user.PatientHistoryRepository;
import com.kasumov.med_ci.model.entity.user.history.PatientHistory;
import com.kasumov.med_ci.service.entity.user.PatientHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientHistoryServiceImpl implements PatientHistoryService {

    private final PatientHistoryRepository patientHistoryRepository;

    @Override
    public PatientHistory save(PatientHistory patientHistory) {
        return patientHistoryRepository.save(patientHistory);
    }

    @Override
    public PatientHistory findPatientHistoryById(long id) {
        return patientHistoryRepository.findPatientHistoriesById(id);
    }
}
