package com.kasumov.med_ci.controller.economist;

import com.kasumov.med_ci.model.dto.user.economist.CurrentEconomistDto;
import com.kasumov.med_ci.model.entity.user.Employee;
import com.kasumov.med_ci.model.response.Response;
import com.kasumov.med_ci.service.dto.user.EconomistDtoService;
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
@PreAuthorize("hasRole('ECONOMIST')")
@RequestMapping("/api/economist")
public class EconomistRestController {

    private final EconomistDtoService economistDtoService;

    @ApiOperation("Авторизованный экономист получает информацию о себе")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Метод возвращает экономиста")
    })
    @GetMapping("/main/current")
    public Response<CurrentEconomistDto> getCurrentEconomistDto() {
        Employee economist = (Employee) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Response.ok(economistDtoService.getCurrentEconomistDto(economist));
    }
}
