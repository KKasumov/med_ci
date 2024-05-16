package com.kasumov.med_ci.controller.economist;

import com.kasumov.med_ci.model.dto.structure.department.DepartmentDto;
import com.kasumov.med_ci.model.enums.AgeType;
import com.kasumov.med_ci.model.response.Response;
import com.kasumov.med_ci.service.dto.structure.DepartmentDtoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ECONOMIST')")
@RequestMapping("api/economist/department")
public class EconomistDepartmentRestController {

    private final DepartmentDtoService departmentDtoService;

    @ApiOperation("Экономист получает все медицинские отделения по параметрам")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Медецинские отделения переданы")
    })
    @GetMapping("/get/medical/by/parameters")
    public Response<List<DepartmentDto>> getMedicalDepartmentsByParameters(
            @RequestParam(required = false) @Nullable AgeType ageType,
            @RequestParam(defaultValue = "") String pattern) {
        return Response.ok(departmentDtoService.getMedicalDepartmentsByParameters(ageType, pattern));
    }
}
