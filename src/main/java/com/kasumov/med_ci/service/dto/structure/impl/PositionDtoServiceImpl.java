package com.kasumov.med_ci.service.dto.structure.impl;

import com.kasumov.med_ci.service.entity.structure.CabinetService;
import com.kasumov.med_ci.service.entity.structure.DepartmentService;
import com.kasumov.med_ci.service.entity.structure.PositionService;
import com.kasumov.med_ci.service.entity.structure.WageService;
import com.kasumov.med_ci.service.entity.user.EquipmentService;
import com.kasumov.med_ci.model.dto.structure.cabinet.converter.CabinetDtoConverter;
import com.kasumov.med_ci.model.dto.structure.position.NewPositionDto;
import com.kasumov.med_ci.model.dto.structure.position.PositionDto;
import com.kasumov.med_ci.model.dto.structure.position.converter.PositionDtoConverter;
import com.kasumov.med_ci.model.dto.structure.wage.converter.WageDtoConverter;
import com.kasumov.med_ci.model.entity.structure.Cabinet;
import com.kasumov.med_ci.model.entity.structure.Department;
import com.kasumov.med_ci.model.entity.structure.Position;
import com.kasumov.med_ci.model.entity.structure.Wage;
import com.kasumov.med_ci.model.entity.user.items.Equipment;
import com.kasumov.med_ci.service.dto.structure.PositionDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PositionDtoServiceImpl implements PositionDtoService {

    private final PositionDtoConverter positionDtoConverter;
    private final PositionService positionService;
    private final EquipmentService equipmentService;
    private final CabinetService cabinetService;
    private final WageDtoConverter wageDtoConverter;
    private final CabinetDtoConverter cabinetDtoConverter;
    private final DepartmentService departmentService;
    private final WageService wageService;

    @Override
    public List<PositionDto> getPositionDtoByDepartmentId(long departmentId) {
        return positionDtoConverter.convertPositionDtoToPosition(
                positionService.getPositionByDepartmentId(departmentId));
    }

    @Override
    @Transactional
    public PositionDto addNewPosition(long departmentId, NewPositionDto newPositionDto) {
        Department department = departmentService.findById(departmentId);
        Cabinet cabinet = cabinetService.findCabinetById(newPositionDto.cabinetId());

        Position position = new Position();
        position.setName(newPositionDto.name());
        position.setDaysForVocation(newPositionDto.daysForVocation());
        position.setDepartment(department);
        position.setCabinet(cabinet);
        positionService.save(position);

        Wage wage = wageDtoConverter.newWageDtoConvertToEntity(newPositionDto.wage());
        wage.setPosition(position);
        wageService.save(wage);

        Equipment equipment = equipmentService.getEquipmentByListId(newPositionDto.equipmentsId());
        equipment.setPosition(position);

        return PositionDto.builder()
                .id(position.getId())
                .name(position.getName())
                .daysForVocation(position.getDaysForVocation())
                .cabinet(cabinetDtoConverter.cabinetToCabinetWithBuildingDtoConverter(cabinet))
                .wage(wageDtoConverter.wageToWageDtoConvert(wage))
                .build();
    }

}
