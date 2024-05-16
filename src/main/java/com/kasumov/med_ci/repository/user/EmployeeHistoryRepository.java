package com.kasumov.med_ci.repository.user;

import com.kasumov.med_ci.model.entity.user.history.EmployeeHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeHistoryRepository extends JpaRepository<EmployeeHistory, Long> {
}
