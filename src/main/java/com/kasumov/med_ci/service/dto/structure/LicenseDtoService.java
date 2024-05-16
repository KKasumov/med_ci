package com.kasumov.med_ci.service.dto.structure;

import com.kasumov.med_ci.model.dto.structure.license.LicenseDto;
import com.kasumov.med_ci.model.dto.structure.license.LicenseForCreateDto;
import com.kasumov.med_ci.model.dto.structure.license.LicenseForUpdateDto;
import com.kasumov.med_ci.model.entity.structure.License;
import com.kasumov.med_ci.model.entity.structure.MedicalOrganization;

import java.util.List;

public interface LicenseDtoService {

    List<LicenseDto> getAllLicense();

    LicenseDto saveLicense(LicenseForCreateDto licenseDto, MedicalOrganization medicalOrganization);

    LicenseDto updateLicense(License license, LicenseForUpdateDto licenseDto);

    void deleteById(Long id);
}
