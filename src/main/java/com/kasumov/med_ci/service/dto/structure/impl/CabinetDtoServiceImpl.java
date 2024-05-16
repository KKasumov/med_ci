package com.kasumov.med_ci.service.dto.structure.impl;

import com.kasumov.med_ci.service.entity.structure.CabinetService;
import com.kasumov.med_ci.model.dto.structure.building.BuildingDtoCabinet;
import com.kasumov.med_ci.model.dto.structure.building.converter.BuildingConverter;
import com.kasumov.med_ci.model.dto.structure.cabinet.NewCabinetDto;
import com.kasumov.med_ci.model.dto.structure.cabinet.converter.CabinetDtoConverter;
import com.kasumov.med_ci.model.entity.structure.Building;
import com.kasumov.med_ci.model.entity.structure.Cabinet;
import com.kasumov.med_ci.model.dto.structure.cabinet.CabinetDto;
import com.kasumov.med_ci.service.dto.structure.CabinetDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class CabinetDtoServiceImpl implements CabinetDtoService {

    private final BuildingConverter buildingConverter;
    private final CabinetDtoConverter cabinetDtoConverter;
    private final CabinetService cabinetService;

    @Override
    public BuildingDtoCabinet saveNewCabinetDTO (Building building, List<NewCabinetDto> newCabinetDto) {
        Set<Cabinet> cabinetSet = cabinetService.saveAllCabinets(cabinetDtoConverter.
                convertNewCabinetDtoListToCabinetSet(newCabinetDto,building));
        return buildingConverter.convertBuildingToBuildingDTO(building,cabinetSet);
    }
    @Override
    @Transactional
    public void updateCabinetsBuildingDto(Cabinet cabinet, CabinetDto cabinetDto) {
        cabinet.setNumber(cabinetDto.number());
        cabinet.setName(cabinetDto.name());
    }
}
