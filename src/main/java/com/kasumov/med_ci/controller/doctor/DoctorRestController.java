package com.kasumov.med_ci.controller.doctor;

import com.kasumov.med_ci.model.dto.user.doctor.CurrentDoctorDto;
import com.kasumov.med_ci.model.entity.user.Doctor;
import com.kasumov.med_ci.model.response.Response;
import com.kasumov.med_ci.service.dto.user.DoctorDtoService;
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
@PreAuthorize("hasRole('DOCTOR')")
@RequestMapping("api/doctor")
public class DoctorRestController {

    private final DoctorDtoService doctorDtoService;

    @ApiOperation("Авторизованный доктор получает информацию о себе")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Метод возвращает доктора")
    })
    @GetMapping("/main/current")
    public Response<CurrentDoctorDto> getCurrentDoctorDto() {
        Doctor doctor = (Doctor) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Response.ok(doctorDtoService.getCurrentDoctorDto(doctor));
    }
}
