package com.kasumov.med_ci.controller.hrmanager;

import com.kasumov.med_ci.model.dto.structure.department.DepartmentWithPositionDto;
import com.kasumov.med_ci.model.dto.structure.position.NewPositionDto;
import com.kasumov.med_ci.model.dto.structure.position.PositionDto;
import com.kasumov.med_ci.model.dto.structure.wage.WageDto;
import com.kasumov.med_ci.model.entity.structure.Department;
import com.kasumov.med_ci.model.entity.structure.Position;
import com.kasumov.med_ci.model.entity.structure.Wage;
import com.kasumov.med_ci.model.exception.BusyConstraintException;
import com.kasumov.med_ci.model.exception.EntityNotFoundException;
import com.kasumov.med_ci.model.exception.InvalidParametersPassedException;
import com.kasumov.med_ci.model.exception.OverdueDateException;
import com.kasumov.med_ci.model.response.Response;
import com.kasumov.med_ci.service.dto.structure.DepartmentDtoService;
import com.kasumov.med_ci.service.dto.structure.PositionDtoService;
import com.kasumov.med_ci.service.dto.structure.WageDtoService;
import com.kasumov.med_ci.service.entity.structure.CabinetService;
import com.kasumov.med_ci.service.entity.structure.DepartmentService;
import com.kasumov.med_ci.service.entity.structure.PositionService;
import com.kasumov.med_ci.service.entity.structure.WageService;
import com.kasumov.med_ci.service.entity.user.EquipmentService;
import com.kasumov.med_ci.service.entity.user.LaborContractService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('HR_MANAGER')")
@RequestMapping("api/manager/position")
public class HrManagerPositionRestController {

    private final WageService wageService;
    private final DepartmentService departmentService;
    private final DepartmentDtoService departmentDtoService;
    private final CabinetService cabinetService;
    private final EquipmentService equipmentService;
    private final PositionDtoService positionDtoService;
    private final WageDtoService wageDtoService;
    private final PositionService positionService;
    private final LaborContractService laborContractService;

    @ApiOperation("кадровик получает информацию по ставкам в отделении")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Получена информация по ставкам в отделении"),
            @ApiResponse(code = 450, message = "Отделения с такм id не существует")
    })
    @GetMapping("/get/information/by/department/{departmentId}")
    public Response<DepartmentWithPositionDto> getAllWageByDepartment(@PathVariable long departmentId) {
        Department department = departmentService.findById(departmentId);
        if (department == null) {
            throw new EntityNotFoundException("Отделения с такм id не существует");
        }
        return Response.ok(departmentDtoService.getInformationByDepartment(department));
    }


    @ApiOperation("Работник кадров создает оклад для отделения")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Оклад успешно создана"),
            @ApiResponse(code = 450, message = "Отделения не существует"),
            @ApiResponse(code = 450, message = "Кабинета не существует"),
            @ApiResponse(code = 450, message = "Оборудование не существует"),
            @ApiResponse(code = 451, message = "Оборудование занято")
    })
    @PostMapping("/add/for/department/{departmentId}")
    public Response<PositionDto> createPositionForDepartment(@PathVariable long departmentId,
                                                             @RequestBody NewPositionDto newPositionDto) {
        if (!departmentService.existsById(departmentId)) {
            throw new EntityNotFoundException("Отделения не существует");
        }
        if (newPositionDto.cabinetId() != null) {
            if (!cabinetService.existsById(newPositionDto.cabinetId())) {
                throw new EntityNotFoundException("Кабинета не существует");
            }
        }
        if (newPositionDto.equipmentsId() != null) {
            if (!equipmentService.isExistsEquipmentsById(newPositionDto.equipmentsId())) {
                throw new EntityNotFoundException("Оборудование не существует");
            }
            if (!equipmentService.isExistCountFreeEquipmentById(newPositionDto.equipmentsId())) {
                throw new BusyConstraintException("Оборудование занято");
            }
        }
        return Response.ok(positionDtoService.addNewPosition(departmentId, newPositionDto));
    }

    @ApiOperation("кадровик редактирует оклад должности")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешное модификация оклада"),
            @ApiResponse(code = 450, message = "Должность с таким ID не найдена"),
            @ApiResponse(code = 450, message = "Оклад с таким ID не найден"),
            @ApiResponse(code = 452, message = "Цена оклада действует в данный момент времени"),
            @ApiResponse(code = 452, message = "Накладка даты действия цены"),
            @ApiResponse(code = 453, message = "Оклад не принадлежит должности")
    })
    @PatchMapping("/{positionId}")
    public Response<WageDto> editWageOfPosition(@PathVariable Long positionId,
                                                @RequestBody WageDto inputDto) {
        Optional<Position> position = positionService.getPositionById(positionId);
        if (position.isEmpty()) {
            throw new EntityNotFoundException("Должность с таким ID не найдена");
        }
        Optional<Wage> wage = wageService.findById(inputDto.id());
        if (wage.isEmpty()) {
            throw new EntityNotFoundException("Оклад с таким ID не найден");
        } else if (!wage.get().getPosition().getId().equals(position.get().getId())) {
            throw new InvalidParametersPassedException("Оклад не принадлежит должности");
        } else if (wage.get().getDateEnd() == null) {
            throw new OverdueDateException("Цена оклада действует в данный момент времени");
        }
        if (laborContractService.findByPositionIdAndDate(position.get().getId(), wage.get().getDateStart(),
                wage.get().getDateEnd()) != null) {
            throw new OverdueDateException("Цена оклада действует в данный момент времени");
        }
        if (wageService.findWagesIdByPositionAndDate(positionId,
                wageDtoService.getLocalDateByString(inputDto.dateStart()),
                wageDtoService.getLocalDateByString(inputDto.dateEnd())).size() > 1) {
            throw new OverdueDateException("Накладка даты действия цены");
        }
        return Response.ok(wageDtoService.updateWageOfPosition(wage.get(), inputDto));
    }
}
