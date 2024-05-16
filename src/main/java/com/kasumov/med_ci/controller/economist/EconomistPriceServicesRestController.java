package com.kasumov.med_ci.controller.economist;

import com.kasumov.med_ci.model.dto.economic.payPriceOfMedicalService.NewPayPriceOfMedicalServiceDto;
import com.kasumov.med_ci.model.dto.economic.payPriceOfMedicalService.PayPriceOfMedicalServiceDto;
import com.kasumov.med_ci.model.dto.medical.medicalService.MedicalServicePriceDto.MedicalServicePriceDto;
import com.kasumov.med_ci.model.entity.medical.MedicalService;
import com.kasumov.med_ci.model.exception.EntityNotFoundException;
import com.kasumov.med_ci.model.response.Response;
import com.kasumov.med_ci.service.dto.economic.MedicalServicePricesDtoService;
import com.kasumov.med_ci.service.dto.economic.PayPriceOfMedicalServiceDtoService;
import com.kasumov.med_ci.service.entity.medical.MedicalServiceService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;


@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ECONOMIST')")
@RequestMapping("api/economist/service")
public class EconomistPriceServicesRestController {

    private final MedicalServiceService medicalServiceService;
    private final PayPriceOfMedicalServiceDtoService payPriceOfMedicalServiceDtoService;
    private final MedicalServicePricesDtoService medicalServicePricesDtoService;

    @ApiOperation("Экономист добавляет цену услуге")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Цена на услугу добавлена"),
            @ApiResponse(code = 450, message = "Услуги с таким id не существует"),
    })
    @PostMapping("/{medicalServiceId}/price/pay/add")
    public Response<PayPriceOfMedicalServiceDto> saveNewPayPriceOfMedicalService(
            @RequestBody NewPayPriceOfMedicalServiceDto newPayPriceOfMedicalServiceDto,
            @PathVariable Long medicalServiceId) {
        MedicalService medicalService = medicalServiceService.findByIdWithDepartment(medicalServiceId);
        if (medicalService == null) {
            throw new EntityNotFoundException("Услуги с таким id не существует");
        }
        return Response.ok(payPriceOfMedicalServiceDtoService
                .saveNewPayPriceOfMedicalServiceDto(newPayPriceOfMedicalServiceDto, medicalService));
    }

     @ApiOperation("Экономист получает цены на мед.услуги")
     @ApiResponses(value = {
            @ApiResponse(code = 450, message = "Услуги с таким id не существует"),
    })
    @GetMapping("/{medicalServiceId}/price/get")
    public Response<MedicalServicePriceDto> economistGetPricesMedicalService(@PathVariable Long medicalServiceId) {
        MedicalService medicalService = medicalServiceService.findByIdWithDepartment(medicalServiceId);
        Optional.ofNullable(medicalService)
                .orElseThrow(() -> new EntityNotFoundException("Услуги с таким id не существует"));
        return Response.ok(medicalServicePricesDtoService.getPricesMedicalService(medicalService));
    }
}
