package com.kasumov.med_ci.repository.user;

import com.kasumov.med_ci.model.entity.user.history.PatientHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientHistoryRepository extends JpaRepository<PatientHistory, Long> {
    public PatientHistory findPatientHistoriesById(long id);
}
