package com.kasumov.med_ci.repository.user;

import com.kasumov.med_ci.model.entity.user.history.DoctorHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorHistoryRepository extends JpaRepository<DoctorHistory, Long> {
}
