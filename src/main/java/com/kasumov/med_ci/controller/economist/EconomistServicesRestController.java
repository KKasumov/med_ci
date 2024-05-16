package com.kasumov.med_ci.controller.economist;

import com.kasumov.med_ci.model.dto.medical.medicalService.MedicalServiceDto;
import com.kasumov.med_ci.model.dto.medical.medicalService.NewMedicalServiceDto;
import com.kasumov.med_ci.model.entity.medical.MedicalService;
import com.kasumov.med_ci.model.exception.BusyConstraintException;
import com.kasumov.med_ci.model.exception.EntityNotFoundException;
import com.kasumov.med_ci.model.response.Response;
import com.kasumov.med_ci.service.dto.medical.MedicalServiceDtoService;
import com.kasumov.med_ci.service.entity.medical.MedicalServiceService;
import com.kasumov.med_ci.service.entity.medical.VisitService;
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

import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ECONOMIST')")
@RequestMapping("api/economist/service")
public class EconomistServicesRestController {

    private final MedicalServiceService medicalServiceService;
    private final MedicalServiceDtoService medicalServiceDtoService;
    private final VisitService visitService;

    @ApiOperation("Экономист добавляет новую услугу")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Услуга добавлена"),
            @ApiResponse(code = 451, message = "Услуга с переданным идентификатором используется")
    })
    @PostMapping("/add/new")
    public Response<MedicalServiceDto> saveNewMedService(@RequestBody NewMedicalServiceDto newMedicalServiceDto) {
        if (medicalServiceService.existByIdentifier(newMedicalServiceDto.identifier())) {
            throw new BusyConstraintException("Услуга с переданным идентификатором используется");
        }
        return Response.ok(medicalServiceDtoService.saveNewServiceDto(newMedicalServiceDto));
    }

    @ApiOperation("Экономист получает медицинские услуги по id департамента или петерну")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Медицинские услуги найдены"),
    })
    @GetMapping("/find/by/parameters")
    public Response<List<MedicalServiceDto>> getMedicalServicesByParameters(@RequestParam String pattern,
                                                                            @RequestParam @Nullable Long departmentId) {
        return Response.ok(medicalServiceDtoService.getMedicalServicesByParameters(pattern, departmentId));
    }

    @ApiOperation("Экономист удаляет медицинскую услугу")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Медицинская услуга удалена"),
            @ApiResponse(code = 450, message = "Медицинской услуги с таким id не существует"),
            @ApiResponse(code = 451, message = "Медицинская услуга используется отделением"),
            @ApiResponse(code = 451, message = "Существуют посещения, связанные с данной услугой")
    })
    @DeleteMapping("/{medicalServiceId}/delete")
    public Response<Void> deleteMedicalServiceById(@PathVariable long medicalServiceId) {
        MedicalService medicalService = medicalServiceService.findByIdWithDepartment(medicalServiceId);
        if (medicalService == null) {
            throw new EntityNotFoundException("Медицинской услуги с таким id не существует");
        }
        if (medicalService.getDepartment() != null) {
            throw new BusyConstraintException("Медицинская услуга используется отделением");
        }
        if (visitService.existsByMedicalServicesId(medicalServiceId)) {
            throw new BusyConstraintException("Существуют посещения, связанные с данной услугой");
        }
        medicalServiceService.deleteById(medicalServiceId);
        return Response.ok();
    }
}