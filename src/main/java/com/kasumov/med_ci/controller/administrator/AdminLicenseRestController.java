package com.kasumov.med_ci.controller.administrator;

import com.kasumov.med_ci.model.dto.structure.license.LicenseDto;
import com.kasumov.med_ci.model.dto.structure.license.LicenseForCreateDto;
import com.kasumov.med_ci.model.dto.structure.license.LicenseForUpdateDto;
import com.kasumov.med_ci.model.entity.structure.License;
import com.kasumov.med_ci.model.entity.structure.MedicalOrganization;
import com.kasumov.med_ci.model.exception.EntityNotFoundException;
import com.kasumov.med_ci.model.response.Response;
import com.kasumov.med_ci.service.dto.structure.LicenseDtoService;
import com.kasumov.med_ci.service.entity.structure.LicenseService;
import com.kasumov.med_ci.service.entity.structure.MedicalOrganizationService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/license")
public class AdminLicenseRestController {

    private final LicenseService licenseService;
    private final LicenseDtoService licenseDtoService;
    private final MedicalOrganizationService medicalOrganizationService;

    @ApiOperation("Admin get info about licenses med organizations")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get list license med organization")
    })
    @GetMapping("/getAll")
    public Response<List<LicenseDto>> getAll() {
        return Response.ok(licenseDtoService.getAllLicense());
    }

    @ApiOperation("Admin create license")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "License created"),
            @ApiResponse(code = 450, message = "MedicalOrganization by id not found")
    })
    @PostMapping("/create")
    public Response<LicenseDto> createLicense(@RequestBody LicenseForCreateDto licenseDto) {
        MedicalOrganization medicalOrganization =
                medicalOrganizationService.findById(licenseDto.medicalOrganizationId());
        if (medicalOrganization == null) {
            throw new EntityNotFoundException("MedicalOrganization by id not found");
        }
        return Response.ok(licenseDtoService.saveLicense(licenseDto, medicalOrganization));
    }

    @ApiOperation("Admin update license med organization")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "License changed"),
            @ApiResponse(code = 450, message = "License by id not found"),
            @ApiResponse(code = 450, message = "MedicalOrganization by id not found")
    })
    @PatchMapping("/update/")
    public Response<LicenseDto> updateLicense(@RequestBody LicenseForUpdateDto licenseDto) {
        Optional<License> license = licenseService.findLicenseById(licenseDto.id());
        if (license.isEmpty()) {
            throw new EntityNotFoundException("License by id not found");
        }
        return Response.ok(licenseDtoService.updateLicense(license.get(), licenseDto));
    }

    @ApiOperation("Admin delete license med organization")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "License deleted"),
            @ApiResponse(code = 450, message = "Entity by id not found")
    })
    @DeleteMapping("/delete/{id}")
    public Response<Void> deleteLicenses(@PathVariable Long id) {
        if (!licenseService.isExistsById(id)) {
            throw new EntityNotFoundException("Entity by id not found");
        }
        licenseDtoService.deleteById(id);
        return Response.ok();
    }

}
