package com.kasumov.med_ci.controller.doctor;

import com.kasumov.med_ci.model.dto.medical.talon.TalonDto;
import com.kasumov.med_ci.model.dto.medical.talon.TalonsByDaysDto;
import com.kasumov.med_ci.model.entity.medical.Talon;
import com.kasumov.med_ci.model.entity.user.Doctor;
import com.kasumov.med_ci.model.exception.BusyConstraintException;
import com.kasumov.med_ci.model.exception.EntityNotFoundException;
import com.kasumov.med_ci.model.response.Response;
import com.kasumov.med_ci.service.dto.medical.TalonDtoService;
import com.kasumov.med_ci.service.entity.medical.TalonService;
import com.kasumov.med_ci.service.entity.user.DoctorService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('DOCTOR')")
@RequestMapping("api/doctor/talon")
public class DoctorTalonRestController {

    private final TalonDtoService talonDtoService;
    private final TalonService talonService;

    private final DoctorService doctorService;

    @ApiOperation("Доктор получает свои талоны на сегодня")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Метод возвращает список талонов доктора текущего дня")
    })
    @GetMapping("/get/today")
    public Response<TalonsByDaysDto> getTalonsByDaysDto() {
        Doctor doctor = (Doctor) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Response.ok(talonDtoService.getCurrentDoctorTodayTalons(doctor));
    }

    @ApiOperation("Доктор получает свои талоны в диапазоне дней")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Метод возвращает все талоны доктора в диапазоне дней")
    })
    @GetMapping("/get/for/days")
    public Response<List<TalonsByDaysDto>> getTalonsByDays(@Nullable String dateStart,
                                                           @Nullable String dateEnd) {
        Doctor doctor = (Doctor) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Response.ok(talonDtoService.getTalonsByDaysAndDoctor(dateStart, dateEnd, doctor));
    }

    @ApiOperation("Доктор удаляет свободные талоны в указанный диапазон дней")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Метод должен вернуть неудаленные талоны")
    })
    @DeleteMapping("/delete/for/days")
    public Response<List<TalonsByDaysDto>> deleteDoctorTalons(@RequestParam @Nullable String dateStart,
                                                              @RequestParam @Nullable String dateEnd) {
        Doctor doctor = (Doctor) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Response.ok(talonDtoService.deleteTalonsCertainTime(dateStart, dateEnd, doctor));
    }

    @ApiOperation("Доктор удаляет свой талон")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Метод удаляет талон"),
            @ApiResponse(code = 450, message = "Талон с таким id не найден"),
            @ApiResponse(code = 451, message = "На талон с таким id записан пациент")
    })
    @DeleteMapping("/{talonId}/delete")
    public Response<HttpStatus> deleteTalonById(@PathVariable Long talonId) {
        Doctor doctor = (Doctor) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Talon talon = talonService.getTalonByIdAndDoctorIdWithDoctor(talonId, doctor.getId());
        if (talon == null) {
            throw new EntityNotFoundException("Талон с таким id не найден");
        }
        if (talon.getPatientHistory() != null) {
            throw new BusyConstraintException("На талон с таким id записан пациент");
        }
        talonService.deleteTalon(talon);
        return Response.ok(HttpStatus.OK);
    }

    @ApiOperation("Доктор получает талон")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Метод возвращет талон, который принадлежит доктору"),
            @ApiResponse(code = 450, message = "Талон с таким id не найден")
    })
    @GetMapping("{talonId}/get")
    public Response<TalonDto> getTalonDto(@PathVariable Long talonId) {
        Doctor doctor = (Doctor) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Talon talon = talonService.getTalonByIdAndDoctorId(talonId,doctor.getId());
        if (talon == null){
            throw new EntityNotFoundException("Талон с таким id не найден");
        }
        return Response.ok(talonDtoService.getTalonDto(talon));
    }

    @ApiOperation("Доктор создает талоны в диапазоне дней")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Талоны созданы")
    })
    @PostMapping("/add/new/for/days")
    public Response<List<TalonsByDaysDto>> createTalonsForRangeDays(@Nullable String dateStart, @Nullable String dateEnd) {
        long doctorId = ((Doctor) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        Doctor doctor = doctorService.findDoctorByIdWithTalons(doctorId);
        return Response.ok(talonDtoService.addTalonsForDoctor(doctor, dateStart, dateEnd));
    }
}
