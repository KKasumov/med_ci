package com.kasumov.med_ci.controller.economist;

import com.kasumov.med_ci.model.dto.medical.disease.DiseaseDto;
import com.kasumov.med_ci.model.entity.medical.Disease;
import com.kasumov.med_ci.model.exception.BusyConstraintException;
import com.kasumov.med_ci.model.exception.EntityNotFoundException;
import com.kasumov.med_ci.model.response.Response;
import com.kasumov.med_ci.service.entity.structure.DepartmentService;
import com.kasumov.med_ci.service.dto.medical.DiseaseDtoService;
import com.kasumov.med_ci.service.entity.medical.AppealService;
import com.kasumov.med_ci.service.entity.medical.DiseaseService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ECONOMIST')")
@RequestMapping("api/economist/disease")
public class EconomistDiseaseRestController {

    private final DiseaseService diseaseService;
    private final AppealService appealService;
    private final DiseaseDtoService diseaseDtoService;
    private final DepartmentService departmentService;

    @ApiOperation("Экономист удаляет заболевание")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Заболевание удалено"),
            @ApiResponse(code = 450, message = "Заболевания с таким id не существует"),
            @ApiResponse(code = 451, message = "Заболевание используется отделением"),
            @ApiResponse(code = 451, message = "Заболевание используется в обращении")
    })
    @DeleteMapping("/{diseaseId}/delete")
    public Response<Void> deleteDiseaseById(@PathVariable long diseaseId) {
        Disease disease = diseaseService.findByIdWithDepartment(diseaseId);
        if (disease == null) {
            throw new EntityNotFoundException("Заболевания с таким id не существует");
        }
        if (disease.getDepartment() != null) {
            throw new BusyConstraintException("Заболевание используется отделением");
        }
        if (appealService.existsByDiseaseId(diseaseId)) {
            throw new BusyConstraintException("Заболевание используется в обращении");
        }
        diseaseService.deleteById(diseaseId);
        return Response.ok();
    }

    @PostMapping("/{diseaseId}/block")
    public Response<DiseaseDto> blockDiseaseById(@PathVariable long diseaseId) {
        if (!diseaseService.existsById(diseaseId)) {
            throw new EntityNotFoundException("Заболевания с таким id не существует");
        }
        return Response.ok(diseaseDtoService.blockedDiseases(diseaseId));
    }

    @PostMapping("/{diseaseId}/unBlock")
    public Response<DiseaseDto> unBlockDiseaseById(@PathVariable long diseaseId) {
        if (!diseaseService.existsById(diseaseId)) {
            throw new EntityNotFoundException("Заболевания с таким id не существует");
        }
        return Response.ok(diseaseDtoService.unBlockedDiseases(diseaseId));
    }

    @ApiOperation("Экономист получает все заболевания по параметрам")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Метод возвращает все заболевания по параметрам"),
            @ApiResponse(code = 450, message = "Мед отделения с таким id не существует")
    })
    @GetMapping("/find/by/parameters")
    public Response<List<DiseaseDto>> findDiseasesByParameters(String pattern, @Nullable Long departmentId) {
        if (departmentId != null && departmentService.isMedicalDepartmentExist(departmentId)) {
            throw new EntityNotFoundException("Мед отделения с таким id не существует");
        }
        return Response.ok(diseaseDtoService.getAllDiseaseDtoByParameters(pattern, departmentId));
    }
}
