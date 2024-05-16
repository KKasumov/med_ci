package com.kasumov.med_ci.model.dto.structure.cabinet;

import lombok.Builder;

@Builder
public record CabinetWithBuildingDto(long cabinetId,
                                    int cabinetNumber,
                                    String cabinetName,
                                    long buildingId,
                                    String buildingPhysicalAddress) {
}
