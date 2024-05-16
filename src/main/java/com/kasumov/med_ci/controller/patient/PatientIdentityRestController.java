package com.kasumov.med_ci.controller.patient;

import com.kasumov.med_ci.model.dto.user.identityDocument.IdentityDocumentDto;
import com.kasumov.med_ci.model.dto.user.identityDocument.NewIdentityDocumentDto;
import com.kasumov.med_ci.model.entity.user.Patient;
import com.kasumov.med_ci.model.response.Response;
import com.kasumov.med_ci.service.dto.user.IdentityDocumentDtoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('PATIENT')")
@RequestMapping("/api/patient")
public class PatientIdentityRestController {

    private final IdentityDocumentDtoService identityDocumentDtoService;

    @ApiOperation("Пациент сохраняет себе новый документ подтверждающий личность")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "новый документ подтверждающий личность пациента сохранен")
    })
    @PostMapping("identity/add/new")
    public Response<List<IdentityDocumentDto>> saveNewIdentity(@RequestBody NewIdentityDocumentDto dto) {
        Patient patient = (Patient) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Response.ok(identityDocumentDtoService.saveNewIdentityByPatient(patient, dto));
    }

}
