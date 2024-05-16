package com.kasumov.med_ci.controller.patient;

import com.kasumov.med_ci.model.dto.structure.department.DepartmentWithDoctorsForPatientDto;
import com.kasumov.med_ci.model.entity.user.Patient;
import com.kasumov.med_ci.service.dto.structure.DepartmentDtoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('PATIENT')")
@RequestMapping("/api/patient/department")
public class PatientDepartmentRestController {

    private final DepartmentDtoService departmentDtoService;

    @ApiOperation("Авторизованный пациент получает подходящие отделения с врачами")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Метод возвращает отделения и врачей подходящие пациенту")
    })
    @GetMapping("/get/all/with/doctors")
    public List<DepartmentWithDoctorsForPatientDto> getAllDepartmentsWithDoctors() {
        Patient patient = (Patient) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return departmentDtoService.getDepartmentsSuitableForAge(patient);
    }
}
