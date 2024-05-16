package com.kasumov.med_ci.controller.registrar;

import com.kasumov.med_ci.model.dto.economic.smo.SmoDto;
import com.kasumov.med_ci.model.response.Response;
import com.kasumov.med_ci.service.dto.economic.SmoDtoService;
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
@RequestMapping("/api/registrar/smo")
public class RegistrarSmoRestController {

    private final SmoDtoService smoDtoService;

    @ApiOperation("Регистратор получает все СМО")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Метод возвращает все СМО")
    })
    @GetMapping("/get/all")
    public Response<List<SmoDto>> getAllSmo() {
        return Response.ok(smoDtoService.getAllSmo());
    }
}