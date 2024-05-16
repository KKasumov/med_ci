package com.kasumov.med_ci.controller.registrar;

import com.kasumov.med_ci.model.dto.user.patient.NewPatientDto;
import com.kasumov.med_ci.model.dto.user.patient.PatientDto;
import com.kasumov.med_ci.model.dto.medical.talon.TalonDto;
import com.kasumov.med_ci.model.dto.testsystem.RequestPatientDtoTestSystem;
import com.kasumov.med_ci.model.dto.testsystem.ResponsePatientDtoTestSystem;
import com.kasumov.med_ci.model.entity.medical.Talon;
import com.kasumov.med_ci.model.entity.structure.Department;
import com.kasumov.med_ci.model.entity.user.Patient;
import com.kasumov.med_ci.model.enums.Gender;
import com.kasumov.med_ci.model.exception.BusyConstraintException;
import com.kasumov.med_ci.model.exception.EntityNotFoundException;
import com.kasumov.med_ci.model.exception.InvalidParametersPassedException;
import com.kasumov.med_ci.model.exception.OutIntegrationException;
import com.kasumov.med_ci.model.exception.OverdueDateException;
import com.kasumov.med_ci.model.response.Response;
import com.kasumov.med_ci.service.dto.medical.TalonDtoService;
import com.kasumov.med_ci.service.dto.testsystem.TestSystemIntegration;
import com.kasumov.med_ci.service.dto.user.PatientDtoService;
import com.kasumov.med_ci.service.entity.economic.SmoService;
import com.kasumov.med_ci.service.entity.medical.TalonService;
import com.kasumov.med_ci.service.entity.structure.DepartmentService;
import com.kasumov.med_ci.service.entity.user.PatientService;
import com.kasumov.med_ci.service.entity.user.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
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
@PreAuthorize("hasRole('REGISTRAR')")
@RequestMapping("/api/registrar/patient")
public class RegistrarPatientRestController {

    private final PatientDtoService patientDtoService;
    private final PatientService patientService;
    private final UserService userService;
    private final SmoService smoService;
    private final TestSystemIntegration testSystemIntegration;
    private final DepartmentService departmentService;
    private final TalonService talonService;
    private final TalonDtoService talonDtoService;

    @ApiOperation("Авторизованный регистратор блокирует пациента")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Метод блокирует пациента"),
    })
    @PatchMapping("/{patientId}/block")
    public Response<PatientDto> blockPatient(@PathVariable Long patientId) {
        if (!patientService.existsById(patientId)) {
            throw new EntityNotFoundException("Пациента с таким id не существует");
        }
        return Response.ok(patientDtoService.setPatientIsEnabled(patientId, false));
    }


    @ApiOperation("Регистратор сохраняет нового пациента")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пациент сохранен в базе"),
            @ApiResponse(code = 450, message = "СМО с таким id не существует"),
            @ApiResponse(code = 451, message = "Пациент с данным email уже существует в базе")
    })
    @PostMapping("/new")
    public Response<PatientDto> saveNewPatient(@RequestBody NewPatientDto newPatientDto) {
        if (userService.existsByEmail(newPatientDto.email())) {
            throw new BusyConstraintException("Пациент с данным email уже существует в базе");
        }
        if (!smoService.existsById(newPatientDto.polis().smoId())) {
            throw new EntityNotFoundException("СМО с таким id не существует");
        }
        return Response.ok(patientDtoService.saveNewPatient(newPatientDto));
    }


    @ApiOperation("Авторизованный регистратор разблокирует пациента")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Метод разблокирует пациента"),
    })
    @PatchMapping("/{patientId}/unblock")
    public Response<PatientDto> unBlockPatient(@PathVariable Long patientId) {
        if (!patientService.existsById(patientId)) {
            throw new EntityNotFoundException("Пациента с таким id не существует");
        }
        return Response.ok(patientDtoService.setPatientIsEnabled(patientId, true));
    }

    @ApiOperation("Регистратор получает пациентов по параметрам")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Метод возвращает пациентов по параметрам")
    })
    @GetMapping("/get/all")
    public Response<List<PatientDto>> getPatientByParameters(@Nullable String firstName,
                                                             @Nullable String lastName,
                                                             @Nullable Gender gender,
                                                             @Nullable String snils,
                                                             @Nullable String polisNumber) {
        return Response.ok(patientDtoService.getPatientByParameters(firstName, lastName, gender, snils, polisNumber));
    }


    @ApiOperation("регистратор записывает пациента в отделение на ближайший прием")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Метод возвращает талон пациента"),
            @ApiResponse(code = 450, message = "Пациент с таким id не существует"),
            @ApiResponse(code = 450, message = "Отделение с таким id не существует"),
            @ApiResponse(code = 453, message = "Возраст пациента не соответствует отделению"),
            @ApiResponse(code = 452, message = "На текущую дату нет свободных талонов")
    })
    @PatchMapping("/{patientId}/assigned/for/nearest/talon/department/{departmentId}")
    public Response<TalonDto> getNearestTalon(@PathVariable Long patientId, @PathVariable Long departmentId) {
        if (!patientService.existsById(patientId)) {
            throw new EntityNotFoundException("Пациент с таким id не существует");
        }
        Patient patient = patientService.getPatientById(patientId);
        Department department = departmentService.findById(departmentId);
        if (department == null) {
            throw new EntityNotFoundException("Отделение с таким id не существует");
        }
        if (department.getAgeType() != null && (department.getAgeType() != patientService.getAgeType(patient))) {
            throw new InvalidParametersPassedException("Возраст пациента не соответствует отделению");
        }
        Talon talon = talonService.getNearestTalonForPatient(departmentId);
        if (talon == null) {
            throw new OverdueDateException("На текущую дату нет свободных талонов");
        }
        return Response.ok(talonDtoService.bookPatientForAppointment(talon, patient));
    }


    @ApiOperation("Метод проверяет пациента в тестовой системе")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пациент проверен"),
            @ApiResponse(code = 457, message = "Пациент не найден")
    })
    @PostMapping(value = "/check/patient")
    public Response<ResponsePatientDtoTestSystem> checkPatientInTestSystem(
            @RequestBody RequestPatientDtoTestSystem requestPatientDtoTestSystem) {
        return Response.ok(
                Optional.ofNullable(testSystemIntegration.checkTokenAndPatientInTestSystem(requestPatientDtoTestSystem))
                        .orElseThrow(() -> new OutIntegrationException("Пациент не найден")));
    }
}
