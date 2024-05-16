package com.kasumov.med_ci.controller.registrar;

import com.kasumov.med_ci.model.dto.user.registrar.CurrentRegistrarDto;
import com.kasumov.med_ci.model.entity.user.Employee;
import com.kasumov.med_ci.model.response.Response;
import com.kasumov.med_ci.service.dto.user.RegistrarDtoService;
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
@PreAuthorize("hasRole('REGISTRAR')")
@RequestMapping("/api/registrar")
public class RegistrarRestController {

    private final RegistrarDtoService registrarDtoService;

    @ApiOperation(value = "Авторизованный регистратор получает информацию о себе")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Метод возвращает регистратора"),
    })
    @GetMapping("/main/current")
    public Response<CurrentRegistrarDto> getCurrentRegistrarDto() {
        Employee registrar = (Employee) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Response.ok(registrarDtoService.getCurrentRegistrarDto(registrar));
    }

}