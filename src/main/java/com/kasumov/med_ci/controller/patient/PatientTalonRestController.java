package com.kasumov.med_ci.controller.patient;

import com.kasumov.med_ci.model.dto.medical.talon.TalonDto;
import com.kasumov.med_ci.model.entity.medical.Talon;
import com.kasumov.med_ci.model.entity.structure.Department;
import com.kasumov.med_ci.model.entity.user.Patient;
import com.kasumov.med_ci.model.exception.EntityNotFoundException;
import com.kasumov.med_ci.model.exception.InvalidParametersPassedException;
import com.kasumov.med_ci.model.exception.OverdueDateException;
import com.kasumov.med_ci.model.response.Response;
import com.kasumov.med_ci.service.dto.medical.TalonDtoService;
import com.kasumov.med_ci.service.entity.medical.TalonService;
import com.kasumov.med_ci.service.entity.user.PatientService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('PATIENT')")
@RequestMapping("/api/patient/talon")
public class PatientTalonRestController {

    private final TalonService talonService;
    private final TalonDtoService talonDtoService;
    private final PatientService patientService;

    @Value("${patientScope}")
    private int patientScope;

    @ApiOperation("пациент записывается на свободный талон доктора")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "талон доктора"),
            @ApiResponse(code = 450, message = "Талон с таким id не существует"),
            @ApiResponse(code = 453, message = "Запись не возможна талон уже занят"),
            @ApiResponse(code = 453, message = "Возраст пациента не соответствует отделению"),
            @ApiResponse(code = 452, message = "дата записи недоступна для пациента"),
    })
    @PatchMapping("/{talonId}/assign")
    public Response<TalonDto> patientAssignFreeTalon(@PathVariable long talonId) {
        Patient patient = (Patient) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Talon talon = talonService.findTalonByPatientHistoryAndDepartmentAndDoctor(talonId);

        if (talon == null) {
            throw new EntityNotFoundException("Талон с таким id не существует");
        }
        Department department = talon.getDoctorHistory().getDepartment();
         if (department.getAgeType() != null && (department.getAgeType() != patientService.getAgeType(patient))) {
            throw new InvalidParametersPassedException("Возраст пациента не соответствует отделению");
        }
        if (talon.getPatientHistory() != null) {
            throw new InvalidParametersPassedException("Запись не возможна талон уже занят");
        }
        if (LocalDateTime.now().plusDays(patientScope).isBefore(talon.getTime())) {
            throw new OverdueDateException("дата записи недоступна для пациента");
        }

        return Response.ok(talonDtoService.assignPatientToFreeTalonDoctor(talon, patient, department));
    }

    @ApiOperation("Пациент получает талоны на которые он записан")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список талонов получен"),
    })
    @PatchMapping("get/all/assigned")
    public Response<List<TalonDto>> patientGetsTalonsAssigned() {
        long patientId = ((Patient) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        return Response.ok(talonDtoService.findPatientTalonsAssigned(patientId));
    }



}

