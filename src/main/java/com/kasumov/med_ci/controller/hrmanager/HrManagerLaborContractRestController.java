package com.kasumov.med_ci.controller.hrmanager;

import com.kasumov.med_ci.model.dto.user.laborContract.LaborUserContractDto;
import com.kasumov.med_ci.util.DateConvertor;
import com.kasumov.med_ci.model.entity.user.items.LaborContract;
import com.kasumov.med_ci.model.exception.EntityNotFoundException;
import com.kasumov.med_ci.model.exception.InvalidParametersPassedException;
import com.kasumov.med_ci.model.response.Response;
import com.kasumov.med_ci.service.dto.user.LaborContractDtoService;
import com.kasumov.med_ci.service.entity.user.LaborContractService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('HR_MANAGER')")
@RequestMapping("/api/manager")
public class HrManagerLaborContractRestController {

    private final LaborContractService laborContractService;

    private final DateConvertor dateConvertor;

    private final LaborContractDtoService laborContractDtoService;

    @ApiOperation("кадровик разрывает трудовой договор")
    @ApiResponses(value = {
            @ApiResponse(code = 450, message = "Трудовой договор не найден по id"),
            @ApiResponse(code = 453, message = "Между указанной датой увольнения должно быть минимум 14 дней"),
            @ApiResponse(code = 453, message = "Вы пытаетесь закрыть уже истекший договор")
    })
    @PatchMapping("/contract/{contractId}/close")
    public Response<LaborUserContractDto> terminationLaborContract(@PathVariable("contractId") long id,
                                                                   @RequestBody String endDate) {
        Optional<LaborContract> laborContract = laborContractService.findById(id);
        if (laborContract.isEmpty()) {
            throw new EntityNotFoundException("Трудовой договор не найден по id");
        }
        if (laborContract.get().getEndDate().compareTo(LocalDate.now()) < 0) {
            throw new InvalidParametersPassedException("Вы пытаетесь закрыть уже истекший договор");
        }
        if (ChronoUnit.DAYS.between(LocalDate.now(), dateConvertor.stringToLocalDate(endDate)) < 14) {
            throw new InvalidParametersPassedException("Между указанной датой увольнения должно быть минимум 14 дней");
        }
        return Response.ok(laborContractDtoService.terminationLaborContract(
                laborContract.get(), dateConvertor.stringToLocalDate(endDate)));
    }

}
