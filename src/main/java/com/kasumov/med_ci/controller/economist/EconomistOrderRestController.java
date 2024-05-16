package com.kasumov.med_ci.controller.economist;

import com.kasumov.med_ci.model.entity.structure.MedicalOrganization;
import com.kasumov.med_ci.model.enums.InsuranceType;
import com.kasumov.med_ci.model.response.Response;
import com.kasumov.med_ci.repository.structure.MedicalOrganizationRepository;
import com.kasumov.med_ci.service.dto.economic.OrderDtoService;
import com.kasumov.med_ci.util.DateConvertor;
import com.kasumov.med_ci.model.dto.economic.order.OrderDto;
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

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;


@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ECONOMIST')")
@RequestMapping("/api/economist/order")
public class EconomistOrderRestController {

    private final OrderDtoService orderDtoService;
    private final DateConvertor dateConvertor;
    private final MedicalOrganizationRepository medicalOrganizationRepository;

    @ApiOperation("Экономист получает счета по переданным параметрам")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Получен список счетов"),
            @ApiResponse(code = 450, message = "Отсутсвует действующая медицинская организация")
    })
    @GetMapping("/get/by/parameters")
    public Response<List<OrderDto>> getAllOrdersByParameters(
            @RequestParam(required = false) @Nullable InsuranceType insuranceType,
            @RequestParam(required = false) @Nullable Boolean isFormed,
            @RequestParam(required = false) @Nullable Boolean isAcceptedForPayment,
            @RequestParam(required = false) @Nullable Boolean isPayed,
            @RequestParam(required = false) @Nullable Long smoId,
            @RequestParam(required = false) @Nullable String dateStart,
            @RequestParam(required = false) @Nullable String dateEnd) {
        return Response.ok(orderDtoService.getAllOrdersByParameters(insuranceType, isFormed, isAcceptedForPayment,
                isPayed, smoId, getStartDate(dateStart), getEndDate(dateEnd)));
    }

    private LocalDate getStartDate(String startDate) {
        if (startDate != null) {
            return dateConvertor.stringToLocalDate(startDate);
        }
        List<MedicalOrganization> allMedicalOrganizations = medicalOrganizationRepository.getAllMedicalOrganizations();
        if (allMedicalOrganizations.isEmpty()) {
            throw new EntityNotFoundException("Отсутствует действующая медицинская организация");
        }
        return allMedicalOrganizations.stream().findFirst().get().getStartDate();
    }

    private LocalDate getEndDate(String endDate) {
        return endDate == null ? LocalDate.now().plusYears(1) : dateConvertor.stringToLocalDate(endDate);
    }
}
