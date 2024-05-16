package com.kasumov.med_ci.controller.registrar;

import com.kasumov.med_ci.util.DateConvertor;
import com.kasumov.med_ci.model.dto.medical.talon.TalonDto;
import com.kasumov.med_ci.model.dto.medical.talon.TalonsByDaysDto;
import com.kasumov.med_ci.model.dto.structure.department.DepartmentWithTalonsByDaysDto;
import com.kasumov.med_ci.model.dto.structure.department.DepartmentWithTalonsDto;
import com.kasumov.med_ci.model.entity.medical.Talon;
import com.kasumov.med_ci.model.entity.user.Doctor;
import com.kasumov.med_ci.model.entity.user.Patient;
import com.kasumov.med_ci.model.enums.AgeType;
import com.kasumov.med_ci.model.exception.BusyConstraintException;
import com.kasumov.med_ci.model.exception.EntityNotFoundException;
import com.kasumov.med_ci.model.exception.InvalidParametersPassedException;
import com.kasumov.med_ci.model.response.Response;
import com.kasumov.med_ci.service.dto.medical.TalonDtoService;
import com.kasumov.med_ci.service.dto.structure.DepartmentDtoService;
import com.kasumov.med_ci.service.entity.medical.TalonService;
import com.kasumov.med_ci.service.entity.structure.DepartmentService;
import com.kasumov.med_ci.service.entity.user.DoctorService;
import com.kasumov.med_ci.service.entity.user.PatientService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PatchMapping;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('REGISTRAR')")
@RequestMapping("/api/registrar/talon")
public class RegistrarTalonRestController {

    private final DepartmentDtoService departmentDtoService;
    private final PatientService patientService;
    private final TalonDtoService talonDtoService;
    private final DoctorService doctorService;
    private final TalonService talonService;
    private final DateConvertor dateConvertor;
    private final DepartmentService departmentService;

    @Value("${patientScope}")
    private int patientScope;

    @Value("${doctorScope}")
    private int doctorScope;

    @ApiOperation("Авторизованный регистратор получает расписание")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Метод возвращает список талонов на сегодня в разрезе отделений"),
    })
    @GetMapping("/get/all/today")
    public Response<List<DepartmentWithTalonsDto>> getAllTalonsByCurrentDate() {
        return Response.ok(departmentDtoService.getDepartmentWithTalonsOnTodayDto());
    }

    @ApiOperation("Метод возвращает все талоны на которые записан пациент")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "талоны возвращены"),
            @ApiResponse(code = 450, message = "Пациента не существует")
    })
    @GetMapping("/get/all/by/patient/{patientId}")
    public Response<List<TalonDto>> getAllPatientTalons(@PathVariable Long patientId) {
        if (!patientService.existsById(patientId)) {
            throw new EntityNotFoundException("Пациента не существует");
        }
        return Response.ok(talonDtoService.getPatientTalons(patientId));
    }

    @ApiOperation("Регистратор перезаписывает пациента на новый талон")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Талон перезаписан"),
            @ApiResponse(code = 450, message = "Старого талона не существует"),
            @ApiResponse(code = 450, message = "Нового талона не существует"),
            @ApiResponse(code = 451, message = "принимающий талон не свободный"),
            @ApiResponse(code = 451, message = "на старый талон не записан пациент"),
            @ApiResponse(code = 451, message = "Талоны не из одного отделения")
    })
    @GetMapping("/{talonId}/assigned/patient/from/old/talon/{oldTalonId}")
    public Response<TalonDto> registersPatientAnotherTalon(@PathVariable Long talonId,
                                                           @PathVariable Long oldTalonId,
                                                           @RequestParam(required = false) boolean isDeleteOldTalon) {

        Talon oldTalon = talonService.findTalonByPatientHistoryAndDepartmentAndDoctor(oldTalonId);
        if (oldTalon == null) {
            throw new EntityNotFoundException("Старого талона не существует");
        }

        Talon newTalon = talonService.findTalonByPatientHistoryAndDepartmentAndDoctor(talonId);
        if (newTalon == null) {
            throw new EntityNotFoundException("Нового талона не существует");
        }

        if (newTalon.getPatientHistory() != null) {
            throw new BusyConstraintException("принимающий талон не свободный");
        }

        if (oldTalon.getPatientHistory() == null) {
            throw new EntityNotFoundException("на старый талон не записан пациент");
        }

        if (!(oldTalon.getDoctorHistory().getDepartment().getId()
                .equals(newTalon.getDoctorHistory().getDepartment().getId()))) {
            throw new BusyConstraintException("Талоны не из одного отделения");
        }
        return Response.ok(talonDtoService.overwritingTalon(newTalon, oldTalon, isDeleteOldTalon));

    }

    @ApiOperation("Метод удаляет свободные талоны доктора в указанный диапазон дней")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "талоны удалены, метод возвращает неудаленные талоны"),
            @ApiResponse(code = 450, message = "Доктора с таким id не существует")
    })
    @DeleteMapping("/delete/free/for/doctor/{doctorId}/by/days")
    public Response<List<TalonsByDaysDto>> deleteFreeDoctorTalons(@PathVariable Long doctorId,
                                                                  @RequestParam @Nullable String dateStart,
                                                                  @RequestParam @Nullable String dateEnd) {
        var doctor = doctorService.findById(doctorId);
        if (doctor.isEmpty()) {
            throw new EntityNotFoundException("Доктора с таким id не существует");
        }
        return Response.ok(talonDtoService.deleteTalonsCertainTime(dateStart, dateEnd, doctor.get()));
    }

    @ApiOperation("Метод создаёт талоны для доктора")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Талоны созданы"),
            @ApiResponse(code = 450, message = "Доктора не существует")
    })
    @PostMapping("/add/for/doctor/{doctorId}/by/days")
    public Response<List<TalonsByDaysDto>> addTalonsForDoctor(@PathVariable long doctorId,
                                                              @Nullable String dayStart,
                                                              @Nullable String dayEnd) {
        Doctor doctor = doctorService.findDoctorByIdWithTalons(doctorId);
        if (doctor == null) {
            throw new EntityNotFoundException("Доктора не существует");
        }
        return Response.ok(talonDtoService.addTalonsForDoctor(doctor, dayStart, dayEnd));
    }

    @ApiOperation("Регистратор удаляет талон")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Метод удаляет талон"),
            @ApiResponse(code = 450, message = "Талон с таким id не найден"),
            @ApiResponse(code = 451, message = "На талон с таким id записан пациент")
    })
    @DeleteMapping("/{talonId}/delete")
    public Response<HttpStatus> deleteTalonById(@PathVariable long talonId) {
        Talon talon = talonService.getTalonById(talonId);
        if (talon == null) {
            throw new EntityNotFoundException("Талон с таким id не найден");
        }
        if (talon.getPatientHistory() != null) {
            throw new BusyConstraintException("На талон с таким id записан пациент");
        }
        talonService.deleteTalon(talon);
        return Response.ok(HttpStatus.OK);
    }

    @ApiOperation("Регистратор получает талоны по параметрам")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Метод вернул талоны по параметрам"),
            @ApiResponse(code = 450, message = "Доктора с таким id не существует"),
            @ApiResponse(code = 450, message = "Департамент не найден"),
            @ApiResponse(code = 453, message = "Диапазон дней больше 3-х, тип мед. учреждения не указан"),
            @ApiResponse(code = 453, message = "Диапазон дней больше 7-ми"),
            @ApiResponse(code = 453, message = "Диапазон дней макс 7 дней"),
            @ApiResponse(code = 453, message = "Диапазон дней макс 10 дней")
    })
    @GetMapping("/get/by/parameters")
    public Response<List<DepartmentWithTalonsByDaysDto>> registrarGetTalonsByParameters(
                                                        @RequestParam(required = false) @Nullable Long departmentId,
                                                        @RequestParam(required = false) @Nullable Long doctorId,
                                                        @RequestParam(required = false) @Nullable String dateStart,
                                                        @RequestParam(required = false) @Nullable String dateEnd,
                                                        @RequestParam(required = false) @Nullable AgeType ageType,
                                                        @RequestParam(required = false) @Nullable Boolean isFree) {
        checkExisting(departmentId, dateStart, dateEnd, ageType, doctorId);
        if (isFree == null) {
            isFree = true;
        }

        return Response.ok(talonDtoService.getTalonsByParameters(departmentId, doctorId,
                                                                    dateStart, dateEnd,
                                                                    ageType, isFree));
    }

    private void checkExisting(Long departmentId, String dateStart, String dateEnd, AgeType ageType, Long doctorId) {
        if (departmentId == null && ageType == null) {
            if (compareDateForException(dateStart, dateEnd, 3)) {
                throw new InvalidParametersPassedException("Диапазон дней больше 3-х, тип мед. учреждения не указан");
            }
        } else if (departmentId == null && ageType != null && doctorId == null) {
            if (compareDateForException(dateStart, dateEnd, 7)) {
                throw new InvalidParametersPassedException("Диапазон дней больше 7-ми");
            }
        }
        if (departmentId != null) {
            if (!departmentService.existsById(departmentId)) {
                throw new EntityNotFoundException("Департамент по id не найден");
            }
            if (departmentService.existsById(departmentId)
                    && compareDateForException(dateStart, dateEnd, patientScope)) {
                throw new InvalidParametersPassedException(stringFormat(patientScope));
            }
        }
        if (doctorId != null) {
            if (!doctorService.existsById(doctorId)) {
                throw new EntityNotFoundException("Доктора с таким id не существует");
            }
            if (compareDateForException(dateStart, dateEnd, doctorScope)) {
                throw new InvalidParametersPassedException(stringFormat(doctorScope));
            }
        }
    }

    private String stringFormat(int scope) {
        return String.format("Диапазон дней макс %s дней", scope);
    }

    private boolean compareDateForException(String dateStart, String dateEnd, int value) {
        return ChronoUnit.DAYS.between(dateConvertor.stringToLocalDateTime(dateStart),
                dateConvertor.stringToLocalDateTime(dateEnd)) > value;
    }

    @ApiOperation("Регистратор записывает пациента на свободный талон")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пациент записан на свободный талон"),
            @ApiResponse(code = 450, message = "Талон с таким ID не существует"),
            @ApiResponse(code = 450, message = "Пациент с таким ID не существует"),
            @ApiResponse(code = 451, message = "Талон используется другим пациентом")
    })
    @PatchMapping("/{talonId}/assigned/by/patient/{patientId}")
    public Response<TalonDto> registersPatientByFreeTalon(@PathVariable Long talonId,
                                                          @PathVariable Long patientId) {
        Talon talon = talonService.findTalonByPatientHistoryAndDepartmentAndDoctor(talonId);
        if (talon == null) {
            throw new EntityNotFoundException("Талон с таким ID не существует");
        }
        Optional<Patient> patient = patientService.findPatientById(patientId);
        if (patient.isEmpty()) {
            throw new EntityNotFoundException("Пациент с таким ID не существует");
        }
        if (talon.getPatientHistory() != null) {
            throw new BusyConstraintException("Талон используется другим пациентом");
        }
        return Response.ok(talonDtoService.registerPatientForFreeTicket(talon, patient.get()));
    }
}
