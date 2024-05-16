package com.kasumov.med_ci.controller.registrar;

import com.kasumov.med_ci.model.dto.medical.talon.TalonDto;
import com.kasumov.med_ci.model.entity.user.Doctor;
import com.kasumov.med_ci.model.exception.EntityNotFoundException;
import com.kasumov.med_ci.model.response.Response;
import com.kasumov.med_ci.service.dto.medical.TalonDtoService;
import com.kasumov.med_ci.service.entity.user.DoctorService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('REGISTRAR')")
@RequestMapping("/api/registrar/doctor")
public class RegistrarDoctorRestController {

    private final DoctorService doctorService;
    private final TalonDtoService talonDtoService;

    @ApiOperation("Регистратор автоматически распределяет пациентов доктора в диапазоне дней и удаляет талоны")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Талоны распределены по другим докторам, неудаленные талоны возвращены"),
            @ApiResponse(code = 450, message = "Доктора с таким id не существует")
    })
    @DeleteMapping("/{doctorId}/assigned/patients/in/department/by/days")
    public Response<List<TalonDto>> distributeDoctorPatientsRangeOfDaysAndRemovesTalon(@PathVariable long doctorId,
                                                                                       @Nullable String dayStart,
                                                                                       @Nullable String dayEnd) {
        Optional<Doctor> doctor = doctorService.findById(doctorId);
        if (doctor.isEmpty()) {
            throw new EntityNotFoundException("Доктор с таким id не существует");
        }
        return Response.ok(talonDtoService.distributePatientAndDeleteTalons(dayStart, dayEnd, doctor.get()));
    }
}
