package com.kasumov.med_ci.controller.patient;

import com.kasumov.med_ci.model.dto.user.patient.PatientDocsDto;
import com.kasumov.med_ci.model.entity.user.Patient;
import com.kasumov.med_ci.model.response.Response;
import com.kasumov.med_ci.service.dto.user.DocumentsDtoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('PATIENT')")
@RequestMapping("/api/patient")
public class PatientDocumentsRestController {

    private final DocumentsDtoService documentsDtoService;

    @ApiOperation("Пациент получает свои документы")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Метод возвращает документы пациента")
    })
    @GetMapping("/documents")
    public Response<PatientDocsDto> getPatientDocs() {
        Patient patient = (Patient) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Response.ok(documentsDtoService.getPatientDocs(patient));
    }
}
