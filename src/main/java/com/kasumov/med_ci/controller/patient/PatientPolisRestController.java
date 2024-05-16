package com.kasumov.med_ci.controller.patient;

import com.kasumov.med_ci.model.dto.user.polis.NewPolisDto;
import com.kasumov.med_ci.model.dto.user.polis.PolisDto;
import com.kasumov.med_ci.model.entity.user.Patient;
import com.kasumov.med_ci.model.exception.EntityNotFoundException;
import com.kasumov.med_ci.model.response.Response;
import com.kasumov.med_ci.service.dto.user.PolisDtoService;
import com.kasumov.med_ci.service.entity.economic.SmoService;
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
public class PatientPolisRestController {

    private final PolisDtoService polisDtoService;
    private final SmoService smoService;

    @ApiOperation("Пациент добавляет новый полис")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Метод сохраняет новый полис пациента"),
            @ApiResponse(code = 450, message = "СМО с таким id не существует")
    })
    @PostMapping("/polis/add/new")
    public Response<List<PolisDto>> saveNewPolis(@RequestBody NewPolisDto newPolisDto) {
        Patient patient = (Patient) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!smoService.existsById(newPolisDto.smoId())) {
            throw new EntityNotFoundException("СМО с таким id не существует");
        }
        return Response.ok(polisDtoService.saveNewPolisByPatient(patient, newPolisDto));
    }
}
