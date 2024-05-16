package com.kasumov.med_ci.controller.registrar;

import com.kasumov.med_ci.model.dto.structure.department.DepartmentWithDoctorsForRegistrarDto;
import com.kasumov.med_ci.model.response.Response;
import com.kasumov.med_ci.service.dto.structure.DepartmentDtoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('REGISTRAR')")
@RequestMapping("api/registrar/department")
public class RegistrarDepartmentRestController {

    private final DepartmentDtoService departmentDtoService;

    @ApiOperation("Авторизованный регистратор получает отделения с врачами")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Метод возвращает отделения с врачами"),
    })
    @GetMapping("/get/all/with/doctors")
    public Response<List<DepartmentWithDoctorsForRegistrarDto>> getAllDepartmentsAndDoctors() {
        return Response.ok(departmentDtoService.getDepartmentsAndDoctorsDto());
    }
}
