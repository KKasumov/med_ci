package com.kasumov.med_ci.controller.registrar;

import com.kasumov.med_ci.model.dto.user.identityDocument.IdentityDocumentDto;
import com.kasumov.med_ci.model.dto.user.identityDocument.NewIdentityDocumentDto;
import com.kasumov.med_ci.model.entity.user.Patient;
import com.kasumov.med_ci.model.exception.EntityNotFoundException;
import com.kasumov.med_ci.model.response.Response;
import com.kasumov.med_ci.service.dto.user.IdentityDocumentDtoService;
import com.kasumov.med_ci.service.entity.user.PatientService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('REGISTRAR')")
@RequestMapping("api/registrar/identity")
public class RegistrarIdentityRestController {

    private final PatientService patientService;
    private final IdentityDocumentDtoService identityDocumentDtoService;

    @ApiOperation("Регистратор сохраняет существующему пациенту новый документ идентификации личности")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Метод сохраняет новый документ для пациента"),
            @ApiResponse(code = 450, message = "Пациента с таким id не существует")
    })
    @PostMapping("/add/new/for/patient/{patientId}")
    public Response<List<IdentityDocumentDto>> registrarAddNewIdentityDocument(
            @PathVariable Long patientId,
            @RequestBody NewIdentityDocumentDto newIdentityDocumentDto) {
        Patient patient = patientService.findPatientById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Пациента с таким id не существует"));
        return Response.ok(identityDocumentDtoService.saveNewIdentityByPatient(patient, newIdentityDocumentDto));
    }
}
