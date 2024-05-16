package com.kasumov.med_ci.service.dto.structure;

import com.kasumov.med_ci.model.dto.structure.building.BuildingDtoCabinet;
import com.kasumov.med_ci.model.dto.structure.cabinet.NewCabinetDto;
import com.kasumov.med_ci.model.entity.structure.Building;

import java.util.List;

import com.kasumov.med_ci.model.dto.structure.cabinet.CabinetDto;
import com.kasumov.med_ci.model.entity.structure.Cabinet;

public interface CabinetDtoService {
     BuildingDtoCabinet saveNewCabinetDTO (Building building, List<NewCabinetDto> newCabinetDto);

     void updateCabinetsBuildingDto(Cabinet cabinet, CabinetDto cabinetDto);
}
