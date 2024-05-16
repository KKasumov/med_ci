package com.kasumov.med_ci.service.entity.user.impl;

import com.kasumov.med_ci.repository.user.UniversityRepository;
import com.kasumov.med_ci.model.entity.user.items.University;
import com.kasumov.med_ci.service.entity.user.UniversityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UniversityServiceImpl implements UniversityService {

    private final UniversityRepository universityRepository;

    @Override
    public University saveNewUniversity(University university) {
        return universityRepository.save(university);
    }

    @Override
    public boolean existsByName(String name) {
        return universityRepository.existsByName(name);
    }

    @Override
    public Optional<University> findById(Long id) {
        return universityRepository.findById(id);
    }

    @Override
    public List<University> getAllUniversities() {
        return universityRepository.findAll();
    }

    @Override
    public University findUniversityById(long id) {
        return universityRepository.findUniversityById(id);
    }

    @Override
    public boolean existsById(long id) {
        return universityRepository.existsById(id);
    }
}
