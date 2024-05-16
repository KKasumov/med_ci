package com.kasumov.med_ci.controller.administrator;

import com.kasumov.med_ci.model.dto.structure.building.BuildingDto;
import com.kasumov.med_ci.model.dto.structure.building.BuildingDtoCabinet;
import com.kasumov.med_ci.model.dto.structure.cabinet.NewCabinetDto;
import com.kasumov.med_ci.model.entity.structure.Building;
import com.kasumov.med_ci.model.exception.EntityNotFoundException;
import com.kasumov.med_ci.model.exception.InvalidParametersPassedException;
import com.kasumov.med_ci.model.exception.SameConstraintException;
import com.kasumov.med_ci.model.response.Response;
import com.kasumov.med_ci.service.dto.structure.BuildingDtoService;
import com.kasumov.med_ci.service.dto.structure.CabinetDtoService;
import com.kasumov.med_ci.service.entity.structure.BuildingService;
import com.kasumov.med_ci.service.entity.structure.MedicalOrganizationService;
import com.kasumov.med_ci.service.entity.structure.PositionService;
import com.kasumov.med_ci.model.dto.structure.building.NewBuildingDto;
import com.kasumov.med_ci.model.exception.BusyConstraintException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/administrator/building")
public class AdministratorBuildingRestController {

    private final BuildingService buildingService;
    private final PositionService positionService;
    private final BuildingDtoService buildingDtoService;
    private final MedicalOrganizationService medicalOrganizationService;
    private final CabinetDtoService cabinetDtoService;


    @ApiOperation("Удаление кабинета")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Кабинет удалён"),
            @ApiResponse(code = 450, message = "Здания не существует"),
            @ApiResponse(code = 450, message = "Кабинета не существует"),
            @ApiResponse(code = 455, message = "Кабинет содержит рабочие места")
    })
    @DeleteMapping("/{buildingId}/delete/cabinets")
    public Response<BuildingDto> deleteCabinetById(@PathVariable long buildingId,
                                                   @RequestBody List<Long> cabinetsId) {
        Building building = buildingService.getCabinetInBuildingById(buildingId, cabinetsId);
        if (building == null) {
            throw new EntityNotFoundException("Здания не существует");
        }
        if (!buildingService.isExistCabinetInBuilding(building, cabinetsId)) {
            throw new EntityNotFoundException("Кабинета не существует");
        }
        if (!positionService.isExistPositionByCabinet(cabinetsId)) {
            throw new SameConstraintException("Кабинет содержит рабочие места");
        }
        return Response.ok(buildingDtoService.deleteCabinetsInBuilding(building, cabinetsId));
    }

    @ApiOperation("Администратор создает новые кабинеты")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "новый кабинет создан"),
            @ApiResponse(code = 450, message = "Здание не найдено по id")
    })
    @PostMapping("/{buildingId}/add/cabinet")
    public Response<BuildingDtoCabinet> saveNewCabinet(@RequestBody List<NewCabinetDto> newCabinetDto,
                                                       @PathVariable Long buildingId) {
        Building building = buildingService.getBuildingById(buildingId);
        if (building == null) {
            throw new EntityNotFoundException("Здание не найдено по id");
        }
        return Response.ok(cabinetDtoService.saveNewCabinetDTO(building, newCabinetDto));
    }

    @ApiOperation("Администратор добавляет новое здание")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Здание создано"),
            @ApiResponse(code = 450, message = "Медицинской организации не существует"),
            @ApiResponse(code = 451, message = "Здание с таким адресом уже существует в системе")
    })

    @PostMapping("/add")
    public Response<BuildingDto> saveNewBuilding(@RequestBody NewBuildingDto newBuildingDto) {
        if (!medicalOrganizationService.existsById(newBuildingDto.medicalOrganizationId())) {
            throw new EntityNotFoundException("Медицинской организации не существует");
        }
        if (buildingService.isExistsByPhysicalAddress(newBuildingDto.physicalAddress())) {
            throw new BusyConstraintException("Здание с таким адресом уже существует в системе");
        }
        return Response.ok(buildingDtoService.saveBuilding(newBuildingDto));
    }

    @ApiOperation("Администратор удаляет здание")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Здание удалено"),
            @ApiResponse(code = 450, message = "Здания с таким id не существует"),
            @ApiResponse(code = 451, message = "Кабинеты этого здания уже используются")
    })
    @DeleteMapping("/{buildingId}/delete")
    public Response<Void> deleteBuildingById(@PathVariable long buildingId) {
        if (!buildingService.isExistsById(buildingId)) {
            throw new EntityNotFoundException("Здания с таким id не существует");
        }
        if (!positionService.isExistPositionByBuilding(buildingId)) {
            throw new BusyConstraintException("Кабинеты этого здания уже используются");
        }
        buildingService.removeBuildingById(buildingId);
        return Response.ok();
    }

    @ApiOperation("Aдминистратор изменяет здание")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешное изменение здания"),
            @ApiResponse(code = 450, message = "Здание с таким ID не найдено"),
            @ApiResponse(code = 453, message = "Некорректные данные в запросе")
    })
    @PatchMapping("/{buildingId}/modify")
    public Response<BuildingDto> editBuilding(@PathVariable Long buildingId,
                                                @RequestBody BuildingDto buildingDto) {
        Building building = buildingService.getBuildingById(buildingId);
        if (building == null) {
            throw new EntityNotFoundException("Здание с таким ID не найдено");
        }
        if (building.getId() != buildingDto.id()) {
            throw new InvalidParametersPassedException("Некорректные данные в запросе");
        }
        return Response.ok(buildingDtoService.updateBuildingDto(building, buildingDto));
    }
}
