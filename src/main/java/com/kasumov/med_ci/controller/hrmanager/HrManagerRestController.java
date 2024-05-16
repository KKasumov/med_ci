package com.kasumov.med_ci.controller.hrmanager;

import com.kasumov.med_ci.model.dto.user.hrmanager.CurrentHrManagerDto;
import com.kasumov.med_ci.model.entity.user.Employee;
import com.kasumov.med_ci.model.response.Response;
import com.kasumov.med_ci.service.dto.user.HrManagerDtoService;
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
@PreAuthorize("hasRole('HR_MANAGER')")
@RequestMapping("/api/manager")
public class HrManagerRestController {

    private final HrManagerDtoService hrManagerDtoService;

    @ApiOperation(value = "Авторизованный кадровик получает информацию о себе")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Метод возвращает кадровика")
    })
    @GetMapping("/main/current")
    public Response<CurrentHrManagerDto> getCurrentHrManagerDto() {
        Employee hrManager = (Employee) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Response.ok(hrManagerDtoService.getCurrentHrManagerDto(hrManager));
    }
}
