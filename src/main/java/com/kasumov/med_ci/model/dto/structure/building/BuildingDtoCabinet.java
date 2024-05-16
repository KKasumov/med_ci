package com.kasumov.med_ci.model.dto.structure.building;

import com.kasumov.med_ci.model.dto.structure.cabinet.CabinetDto;
import lombok.Builder;

import javax.annotation.Nullable;
import java.util.List;

@Builder
public record BuildingDtoCabinet(Long id,
                                 String physicalAddress,
                                 @Nullable List<CabinetDto> cabinets) {
}
