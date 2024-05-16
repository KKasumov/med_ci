package com.kasumov.med_ci.service.entity.user;

import com.kasumov.med_ci.model.entity.user.items.University;

import java.util.List;
import java.util.Optional;

public interface UniversityService {

    University saveNewUniversity(University university);

    boolean existsByName(String name);

    Optional<University> findById(Long id);

    List<University> getAllUniversities();

    University findUniversityById(long id);

    boolean existsById(long id);
}
