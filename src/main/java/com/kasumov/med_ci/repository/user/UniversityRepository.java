package com.kasumov.med_ci.repository.user;

import com.kasumov.med_ci.model.entity.user.items.University;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UniversityRepository extends JpaRepository<University, Long> {

    boolean existsByName(String name);

    University findUniversityById(long id);
}