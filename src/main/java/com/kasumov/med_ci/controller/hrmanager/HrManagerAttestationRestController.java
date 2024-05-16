package com.kasumov.med_ci.controller.hrmanager;

import com.kasumov.med_ci.model.dto.user.attestation.AttestationDtoAllDoctorByDateto;
import com.kasumov.med_ci.model.dto.user.attestation.AttestationDtoWithUniversityAndContract;
import com.kasumov.med_ci.model.dto.user.attestation.NewAttestationDto;
import com.kasumov.med_ci.model.exception.BusyConstraintException;
import com.kasumov.med_ci.model.entity.structure.Department;
import com.kasumov.med_ci.model.exception.EntityNotFoundException;
import com.kasumov.med_ci.model.exception.InvalidParametersPassedException;
import com.kasumov.med_ci.model.response.Response;
import com.kasumov.med_ci.service.dto.user.AttestationDtoService;
import com.kasumov.med_ci.service.dto.structure.DepartmentDtoService;
import com.kasumov.med_ci.service.entity.structure.DepartmentService;
import com.kasumov.med_ci.service.entity.user.AttestationService;
import com.kasumov.med_ci.service.entity.user.LaborContractService;
import com.kasumov.med_ci.service.entity.user.UniversityService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController()
@RequiredArgsConstructor
@PreAuthorize("hasRole('HR_MANAGER')")
@RequestMapping("api/manager/attestation")
public class HrManagerAttestationRestController {

    private final AttestationService attestationService;
    private final UniversityService universityService;
    private final LaborContractService laborContractService;
    private final AttestationDtoService attestationDtoService;
    private final DepartmentService departmentService;
    private final DepartmentDtoService departmentDtoService;


    @ApiOperation("Авторизованный работник кадров удаляет аттестацию у доктора")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешное удаление аттестации"),
            @ApiResponse(code = 450, message = "Аттестация с таким ID не найдена")
    })
    @DeleteMapping(value = "/{attestationId}")
    public Response<Void> deleteAttestation(@PathVariable long attestationId) {
        if (!attestationService.existsById(attestationId)) {
            throw new EntityNotFoundException("Аттестации с таким id не найдена");
        }
        attestationService.deleteById(attestationId);
        return Response.ok();
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Аттестация успешно создана"),
            @ApiResponse(code = 451, message = "Аттестация уже существует в системе"),
            @ApiResponse(code = 450, message = "Университета с таким id не найдено"),
            @ApiResponse(code = 450, message = "Договора с таким id не найдено"),
            @ApiResponse(code = 453, message = "Договор уже просрочен"),
            @ApiResponse(code = 453, message = "Договор принадлежит не доктору")
    })
    @PostMapping("/add/for/contract/{contractId}")
    public Response<AttestationDtoWithUniversityAndContract> saveAttestationByContract(
            @RequestBody NewAttestationDto newAttestationDto,
            @PathVariable long contractId) {
        if (attestationService.existsByNumber(newAttestationDto.number())) {
            throw new BusyConstraintException("Аттестация уже существует в системе");
        }
        if (!universityService.existsById(newAttestationDto.universityId())) {
            throw new EntityNotFoundException("Университета с таким id не найдено");
        }
        if (!laborContractService.existsLaborContractById(contractId)) {
            throw new EntityNotFoundException("Договора с таким id не найдено");
        }
        if (laborContractService.findLaborContractById(contractId).getEndDate().compareTo(LocalDate.now()) < 0) {
            throw new InvalidParametersPassedException("Договор уже просрочен");
        }
        if (!laborContractService.getRoleOfOwnerLaborContract(contractId).equals("DOCTOR")) {
            throw new InvalidParametersPassedException("Договор принадлежит не доктору");
        }
        return Response.ok(attestationDtoService.saveAttestationByContract(newAttestationDto, contractId));
    }

    @ApiOperation("Кадровик получает всех врачей у которых истекает аттестация")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Получены все врачи у которых истекает аттестация"),
            @ApiResponse(code = 450, message = "Отделения с таким id не существует")
    })
    @GetMapping(value = "/attention/doctors/by/parameters")
    public Response <AttestationDtoAllDoctorByDateto> getDoctorAllByEndAttestation(@RequestParam @Nullable Long departmentId,
                                                                                   @RequestParam @Nullable Integer day) {
        Department department = departmentService.getDepartmentBysId(departmentId);
        if (department == null && departmentId != null) {
            throw new EntityNotFoundException("Отделения с таким id не существует");
        }
        return Response.ok(departmentDtoService.getAttestationDtoAllDoctorByDate(departmentId, day, department));
    }
}
