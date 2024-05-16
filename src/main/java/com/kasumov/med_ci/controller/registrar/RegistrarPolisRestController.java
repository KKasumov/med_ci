package com.kasumov.med_ci.controller.registrar;

import com.kasumov.med_ci.model.dto.user.polis.PolisDto;
import com.kasumov.med_ci.model.response.Response;
import com.kasumov.med_ci.service.dto.user.PolisDtoService;
import com.kasumov.med_ci.service.entity.user.PatientService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('REGISTRAR')")
@RequestMapping("/api/registrar/polis")
public class RegistrarPolisRestController {

    private final PatientService patientService;
    private final PolisDtoService polisDtoService;

    @ApiOperation("Регистратор получает все полисы пациента")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Метод возвращает все полисы пациента")
    })
    @GetMapping("/for/patient/{patientId}/get/all")
    public Response<List<PolisDto>> getAllPatientPolis(@PathVariable Long patientId) {
        if (!patientService.existsById(patientId)) {
            throw new EntityNotFoundException("Пациента с таким id не существует");
        }
        return Response.ok(polisDtoService.getAllPatientPolis(patientId));
    }
}
