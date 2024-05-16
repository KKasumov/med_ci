package com.kasumov.med_ci.controller.hrmanager;

import com.kasumov.med_ci.model.dto.structure.building.BuildingDto;
import com.kasumov.med_ci.model.response.Response;
import com.kasumov.med_ci.service.dto.structure.BuildingDtoService;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('HR_MANAGER')")
@RequestMapping("api/manager/building")
public class HrManagerBuildingRestController {

    private final BuildingDtoService buildingDtoService;

    @ApiResponse(code = 200, message = "Метод возвращает все здания одной организации")
    @GetMapping("/get/all")
    public Response<List<BuildingDto>> getAllBuildings() {
        return Response.ok(buildingDtoService.getAllBuildings());
    }
}
