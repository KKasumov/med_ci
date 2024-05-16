package com.kasumov.med_ci.controller.testsystem;

import com.kasumov.med_ci.model.dto.testsystem.RequestPatientDtoTestSystem;
import com.kasumov.med_ci.model.dto.testsystem.ResponsePatientDtoTestSystem;
import com.kasumov.med_ci.model.response.Response;
import com.kasumov.med_ci.service.dto.testsystem.TestSystemIntegration;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("api/doctor")
public class TestSystemRestController {

    private final TestSystemIntegration testSystemIntegration;

    //todo эндпоинт должен находиться в контроллере для регистратора
    @ApiOperation("Метод проверяет пациента в тестовой системе")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пациент проверен"),
    })
    @PostMapping(value = "/check/patient")
    public Response<ResponsePatientDtoTestSystem> checkPatientInTestSystem(
            @RequestBody RequestPatientDtoTestSystem requestPatientDtoTestSystem) {
        return Response.ok(testSystemIntegration.checkTokenAndPatientInTestSystem(requestPatientDtoTestSystem));
    }
}
