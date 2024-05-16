package com.kasumov.med_ci.repository.user;

import com.kasumov.med_ci.model.entity.user.items.Vacation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VacationRepository extends JpaRepository<Vacation, Long> {
}
