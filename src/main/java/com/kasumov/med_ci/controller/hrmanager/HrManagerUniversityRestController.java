package com.kasumov.med_ci.controller.hrmanager;

import com.kasumov.med_ci.model.dto.user.university.UniversityDto;
import com.kasumov.med_ci.model.dto.user.university.UniversityDtoShort;
import com.kasumov.med_ci.model.exception.BusyConstraintException;
import com.kasumov.med_ci.model.response.Response;
import com.kasumov.med_ci.service.dto.user.UniversityDtoService;
import com.kasumov.med_ci.service.entity.user.UniversityService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('HR_MANAGER')")
@RequestMapping("api/manager/university")

public class HrManagerUniversityRestController {

    private final UniversityService universityService;
    private final UniversityDtoService universityDtoService;

    @ApiOperation("Кадровик добавляет новый университет")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Новый университет добавлен"),
            @ApiResponse(code = 451, message = "Университет существует в системе"),
    })
    @PostMapping("/add/new")
    public Response<UniversityDto> saveNewUniversity(@RequestBody UniversityDtoShort universityDtoShort) {
        if (universityService.existsByName(universityDtoShort.name())) {
            throw new BusyConstraintException("Университет существует в системе");
        }
        return Response.ok(universityDtoService.saveNewUniversityDto(universityDtoShort));
    }

    @ApiOperation("Кадровик получает все университеты")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Университеты получены")
    })
    @GetMapping("get/all")
    public Response<List<UniversityDto>> getAllUniversities() {
        return Response.ok(universityDtoService.getAllUniversities());
    }

}
