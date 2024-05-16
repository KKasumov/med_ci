package com.kasumov.med_ci.model.dto.structure.building;

import com.kasumov.med_ci.model.dto.structure.cabinet.NewCabinetDto;
import lombok.Builder;
import org.springframework.lang.Nullable;

import java.util.List;

@Builder
public record NewBuildingDto(String physicalAddress,
                             long medicalOrganizationId,
                             @Nullable List<NewCabinetDto> cabinets) {
}
