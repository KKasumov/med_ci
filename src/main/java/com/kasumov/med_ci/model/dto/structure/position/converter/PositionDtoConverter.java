package com.kasumov.med_ci.model.dto.structure.position.converter;

import com.kasumov.med_ci.model.dto.user.employee.EmployeeInformationDto;
import com.kasumov.med_ci.model.dto.user.employee.converter.EmployeeDtoConverter;
import com.kasumov.med_ci.model.entity.structure.Position;
import com.kasumov.med_ci.model.dto.structure.cabinet.CabinetWithBuildingDto;
import com.kasumov.med_ci.model.dto.structure.cabinet.converter.CabinetDtoConverter;
import com.kasumov.med_ci.model.dto.structure.position.PositionDto;
import com.kasumov.med_ci.model.dto.structure.wage.WageDto;
import com.kasumov.med_ci.model.dto.structure.wage.converter.WageDtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PositionDtoConverter {

    private final CabinetDtoConverter cabinetDtoConverter;
    private final WageDtoConverter wageDtoConverter;
    private final EmployeeDtoConverter employeeDtoConverter;

    public PositionDto convertFromEntity(Position position) {
        return PositionDto.builder()
                .id(position.getId())
                .name(position.getName())
                .daysForVocation(position.getDaysForVocation())
                .build();
    }

    public List<PositionDto> convertPositionDtoToPosition(List<Position> positions) {
        return positions.stream()
                .map(this::convertPositionDtoToPosition)
                .toList();
    }

    private PositionDto convertPositionDtoToPosition(Position position) {
        return PositionDto.builder()
                .id(position.getId())
                .name(position.getName())
                .daysForVocation(position.getDaysForVocation())
                .cabinet(createCabinet(position))
                .employee(createEmployeeHistory(position))
                .wage(createWageDto(position))
                .build();
    }

    private CabinetWithBuildingDto createCabinet(Position position) {
        return cabinetDtoConverter.convertCabinetToCabinetWithBuildingDto(position);
    }

    private EmployeeInformationDto createEmployeeHistory(Position position) {
        return employeeDtoConverter.convertEmployeeHistorySetToEmployeeInfDto(position);
    }

    private WageDto createWageDto(Position position) {
        return wageDtoConverter.convertToListWageToWageDto(position);
    }
}