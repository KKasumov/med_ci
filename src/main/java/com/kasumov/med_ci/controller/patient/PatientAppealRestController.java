package com.kasumov.med_ci.controller.patient;

import com.kasumov.med_ci.model.dto.medical.appeal.AppealForPatientNativeFullDto;
import com.kasumov.med_ci.model.entity.medical.Appeal;
import com.kasumov.med_ci.model.entity.user.Patient;
import com.kasumov.med_ci.model.exception.BusyConstraintException;
import com.kasumov.med_ci.model.exception.EntityNotFoundException;
import com.kasumov.med_ci.model.response.Response;
import com.kasumov.med_ci.service.dto.medical.AppealDtoService;
import com.kasumov.med_ci.service.entity.medical.AppealService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('PATIENT')")
@RequestMapping("/api/patient/appeal")
public class PatientAppealRestController {

    private final AppealService appealService;
    private final AppealDtoService appealDtoService;

    @ApiOperation("Пациент получает полную информацию по своему обращению")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Метод возвращает полную информацию по обращению"),
            @ApiResponse(code = 450, message = "Обращение не существует"),
            @ApiResponse(code = 451, message = "Обращение не принадлежит пациенту")
    })
    @GetMapping("/{appealId}/get")
    public Response<AppealForPatientNativeFullDto> getCompleteInformationOnAppeal(@PathVariable Long appealId) {
        Patient patient = (Patient) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //проверяем существует ли такое обращение
        if (!appealService.existsById(appealId)) {
            throw new EntityNotFoundException("Обращение не существует");
        }
        //проверяем принадлежит ли обращение пациенту
        Appeal appeal = appealService.findWithPatient(appealId);
        if (!appeal.getPatientHistory().getPatient().getId().equals(patient.getId())) {
            throw new BusyConstraintException("Обращение не принадлежит пациенту");
        }
        return Response.ok(appealDtoService.getFullInfoAboutAppeal(appealId));
    }
}
