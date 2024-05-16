package com.kasumov.med_ci.repository.user;

import com.kasumov.med_ci.model.entity.user.history.UserHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserHistoryRepository extends JpaRepository<UserHistory, Long> {
}
