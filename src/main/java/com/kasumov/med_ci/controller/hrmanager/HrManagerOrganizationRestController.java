package com.kasumov.med_ci.controller.hrmanager;

import com.kasumov.med_ci.model.dto.structure.medicalOrganization.MedicalOrganizationDto;
import com.kasumov.med_ci.model.dto.structure.medicalOrganization.OrganizationWithDepartmentsDto;
import com.kasumov.med_ci.model.entity.structure.MedicalOrganization;
import com.kasumov.med_ci.model.entity.user.Doctor;
import com.kasumov.med_ci.model.exception.BusyConstraintException;
import com.kasumov.med_ci.model.exception.EntityNotFoundException;
import com.kasumov.med_ci.model.exception.InvalidParametersPassedException;
import com.kasumov.med_ci.model.response.Response;
import com.kasumov.med_ci.service.dto.structure.MedicalOrganizationDtoService;
import com.kasumov.med_ci.service.entity.structure.MedicalOrganizationService;
import com.kasumov.med_ci.service.entity.user.DoctorService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import static com.kasumov.med_ci.model.enums.RolesEnum.CHIEF_DOCTOR;


@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('HR_MANAGER')")
@RequestMapping("/api/manager/organization")
public class HrManagerOrganizationRestController {

    private final MedicalOrganizationService medicalOrganizationService;
    private final MedicalOrganizationDtoService medicalOrganizationDtoService;
    private final DoctorService doctorService;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Метод возвращает организацию с её отделениями")
    })
    @GetMapping("/get/with/departments")
    public Response<OrganizationWithDepartmentsDto> getOrganizationWithDepartments() {
        return Response.ok(medicalOrganizationDtoService.getOrganizationWithDepartments());
    }

    @ApiOperation(value = "кадровик назначает главного врача")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Главный врач назначен"),
            @ApiResponse(code = 450, message = "Доктора с таким id не существует"),
            @ApiResponse(code = 451, message = "Доктор является зав.отделением"),
            @ApiResponse(code = 453, message = "У доктора нет действующего трудового договора"),
            @ApiResponse(code = 451, message = "Доктор уже занимает эту должность"),
            @ApiResponse(code = 450, message = "Мед.организации с таким id не существует")
    })
    @PatchMapping("/{organizationId}/add/director/{doctorId}")
    public Response<MedicalOrganizationDto> appointDirector(@PathVariable long organizationId,
                                                            @PathVariable long doctorId) {
        var newDirector = doctorService.getDoctorWithRole(doctorId);
        if (newDirector.isEmpty()) {
            throw new EntityNotFoundException("Доктора с таким id не существует");
        }
        if (newDirector.get().getRole().getName().equals(CHIEF_DOCTOR.name())) {
            throw new BusyConstraintException("Доктор является зав.отделением");
        }
        if (doctorService.isExistDoctorByLaborContractValid(doctorId)) {
            throw new InvalidParametersPassedException("У доктора нет действующего трудового договора");
        }
        MedicalOrganization medicalOrganization = medicalOrganizationService.findById(organizationId);
        if (medicalOrganization == null) {
            throw new EntityNotFoundException("Мед.организации с таким id не существует");
        }
        Doctor currentDirector = medicalOrganization.getDirector();
        if (currentDirector != null && currentDirector.getId() == doctorId) {
            throw new BusyConstraintException("Доктор уже занимает эту должность");
        }
        return Response.ok(
                medicalOrganizationDtoService.setOrganizationDirector(medicalOrganization, newDirector.get()));
    }
    @ApiOperation(value = "кадровик убирает главного врача")
    @ApiResponses(value = {
            @ApiResponse(code = 450, message = "Медицинской организации с таким id не существует"),
            @ApiResponse(code = 450, message = "У медицинской организации нет главного врача")
    })
    @PatchMapping("{organizationId}/remove/director")
    public Response<MedicalOrganizationDto> removeDirector(@PathVariable long organizationId) {
        MedicalOrganization medicalOrganization = medicalOrganizationService.findById(organizationId);
        if (medicalOrganization == null) {
            throw new EntityNotFoundException("Медицинской организации с таким id не существует");
        }
        if (medicalOrganization.getDirector() == null) {
            throw new EntityNotFoundException("У медицинской организации нет главного врача");
        }
        return Response.ok(medicalOrganizationDtoService.removeDirector(medicalOrganization));
    }

    @ApiOperation(value = "Кадровик редактирует мед. учреждение")
    @ApiResponses(value = {
            @ApiResponse(code = 450, message = "Мед.организации с таким id не существует")
    })
    @PatchMapping("/update")
    public Response<MedicalOrganizationDto> updateMedicalOrganization(
            @RequestBody MedicalOrganizationDto medicalOrganizationDto) {
        MedicalOrganization medicalOrganization = medicalOrganizationService.findById(medicalOrganizationDto.id());
        if (medicalOrganization == null) {
            throw new EntityNotFoundException("Мед.организации с таким id не существует");
        }
        return Response
                .ok(medicalOrganizationDtoService.updateOrganization(medicalOrganizationDto, medicalOrganization));
    }
}
