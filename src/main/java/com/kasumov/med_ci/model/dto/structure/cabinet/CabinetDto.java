package com.kasumov.med_ci.model.dto.structure.cabinet;

import lombok.Builder;

@Builder
public record CabinetDto(long id,
                         int number,
                         String name) {
}
