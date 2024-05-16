package com.kasumov.med_ci.service.entity.structure;

import com.kasumov.med_ci.model.entity.structure.License;

import java.util.List;
import java.util.Optional;

public interface LicenseService {

    License saveLicense(License license);

    void deleteLicenseById(Long id);

    List<License> getAllLicense();

    Optional<License> findLicenseById(Long id);

    boolean isExistsById(Long id);

}
