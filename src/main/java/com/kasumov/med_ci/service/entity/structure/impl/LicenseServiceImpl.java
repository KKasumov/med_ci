package com.kasumov.med_ci.service.entity.structure.impl;

import com.kasumov.med_ci.repository.structure.LicenseRepository;
import com.kasumov.med_ci.model.entity.structure.License;
import com.kasumov.med_ci.service.entity.structure.LicenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LicenseServiceImpl implements LicenseService {

    private final LicenseRepository licenseRepository;

    @Transactional
    @Override
    public License saveLicense(License license) {
        return licenseRepository.save(license);
    }

    @Transactional
    @Override
    public void deleteLicenseById(Long id) {
        licenseRepository.deleteById(id);
    }

    @Override
    public List<License> getAllLicense() {
        return licenseRepository.getAllLicenses();
    }

    @Override
    public Optional<License> findLicenseById(Long id) {
        return licenseRepository.findById(id);
    }

    @Override
    public boolean isExistsById(Long id) {
        return licenseRepository.existsById(id);
    }
}
