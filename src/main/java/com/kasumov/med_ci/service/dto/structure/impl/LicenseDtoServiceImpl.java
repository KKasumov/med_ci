package com.kasumov.med_ci.service.dto.structure.impl;

import com.kasumov.med_ci.service.entity.structure.LicenseService;
import com.kasumov.med_ci.util.DateConvertor;
import com.kasumov.med_ci.model.dto.structure.license.LicenseDto;
import com.kasumov.med_ci.model.dto.structure.license.LicenseForCreateDto;
import com.kasumov.med_ci.model.dto.structure.license.LicenseForUpdateDto;
import com.kasumov.med_ci.model.dto.structure.license.converter.LicenseDtoConverter;
import com.kasumov.med_ci.model.entity.structure.License;
import com.kasumov.med_ci.model.entity.structure.MedicalOrganization;
import com.kasumov.med_ci.service.dto.structure.LicenseDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LicenseDtoServiceImpl implements LicenseDtoService {

    private final LicenseDtoConverter licenseDtoConverter;
    private final LicenseService licenseService;
    private final DateConvertor dateConvertor;

    @Override
    public List<LicenseDto> getAllLicense() {
        return licenseService
                .getAllLicense()
                .stream()
                .map(licenseDtoConverter::convertEntityToDto)
                .sorted(Comparator.comparing(LicenseDto::number))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public LicenseDto saveLicense(LicenseForCreateDto licenseDto, MedicalOrganization medicalOrganization) {
        License license = licenseDtoConverter.convertToEntity(licenseDto);
        license.setMedicalOrganization(medicalOrganization);
        return licenseDtoConverter.convertEntityToDto(licenseService.saveLicense(license));
    }

    @Override
    @Transactional
    public LicenseDto updateLicense(License license, LicenseForUpdateDto licenseDto) {
        license.setName(licenseDto.name());
        license.setNumber(licenseDto.number());
        license.setDateFrom(dateConvertor.stringToLocalDate(licenseDto.startDate()));
        license.setDateTo(dateConvertor.stringToLocalDate(licenseDto.endDate()));
        return licenseDtoConverter.convertEntityToDto(licenseService.saveLicense(license));
    }

    @Override
    public void deleteById(Long id) {
        licenseService.deleteLicenseById(id);
    }
}
