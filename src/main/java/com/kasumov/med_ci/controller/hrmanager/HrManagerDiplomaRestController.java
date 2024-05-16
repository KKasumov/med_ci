package com.kasumov.med_ci.controller.hrmanager;

import com.kasumov.med_ci.model.dto.user.diploma.DiplomaDtoWithUniversityAndContract;
import com.kasumov.med_ci.model.dto.user.diploma.DiplomaForHiringDto;
import com.kasumov.med_ci.model.exception.BusyConstraintException;
import com.kasumov.med_ci.model.exception.EntityNotFoundException;
import com.kasumov.med_ci.model.exception.InvalidParametersPassedException;
import com.kasumov.med_ci.model.response.Response;
import com.kasumov.med_ci.service.dto.user.DiplomaDtoService;
import com.kasumov.med_ci.service.entity.user.DiplomaService;
import com.kasumov.med_ci.service.entity.user.LaborContractService;
import com.kasumov.med_ci.service.entity.user.UniversityService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('HR_MANAGER')")
@RequestMapping("/api/manager/diploma")
public class HrManagerDiplomaRestController {

    private final DiplomaService diplomaService;
    private final UniversityService universityService;
    private final LaborContractService laborContractService;
    private final DiplomaDtoService diplomaDtoService;

    @ApiOperation(value = "Кадровик удаляет диплом сотрудника")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Диплом удален"),
            @ApiResponse(code = 450, message = "Диплом с таким Id не найден"),
    })
    @DeleteMapping("/{diplomaId}")
    public Response<HttpStatus> deleteDiplomaById(@PathVariable long diplomaId) {
        if (!diplomaService.existsById(diplomaId)) {
            throw new EntityNotFoundException("Диплом с таким Id не найден");
        }
        diplomaService.deleteDiplomaById(diplomaId);
        return Response.ok(HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Диплом успешно длбавлен"),
            @ApiResponse(code = 451, message = "Диплом уже существует в системе"),
            @ApiResponse(code = 450, message = "Университета с таким id не найдено"),
            @ApiResponse(code = 450, message = "Договора с таким id не найдено"),
            @ApiResponse(code = 453, message = "Договор уже просрочен"),
            @ApiResponse(code = 453, message = "Договор принадлежит не сотруднику")
    })
    @PostMapping("/add/for/contract/{contractId}")
    public Response<DiplomaDtoWithUniversityAndContract> saveDiplomaByContract(@RequestBody DiplomaForHiringDto newDiplomaDto,
                                                                               @PathVariable long contractId) {
        if (diplomaService.existsBySerialNumber(newDiplomaDto.serialNumber())) {
            throw new BusyConstraintException("Диплом уже существует в системе");
        }
        if (!universityService.existsById(newDiplomaDto.universityId())) {
            throw new EntityNotFoundException("Университета с таким id не найдено");
        }
        if (!laborContractService.existsLaborContractById(contractId)) {
            throw new EntityNotFoundException("Договора с таким id не найдено");
        }
        if (laborContractService.findLaborContractById(contractId).getEndDate().compareTo(LocalDate.now()) < 0
                || laborContractService.findLaborContractById(contractId).getEndDate() == null) {
            throw new InvalidParametersPassedException("Договор уже просрочен");
        }

        return Response.ok(diplomaDtoService.saveDiplomaByContract(newDiplomaDto, contractId));
    }

}
