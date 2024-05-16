package com.kasumov.med_ci.repository.user;

import com.kasumov.med_ci.model.entity.user.items.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
