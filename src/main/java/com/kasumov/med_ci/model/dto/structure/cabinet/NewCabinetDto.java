package com.kasumov.med_ci.model.dto.structure.cabinet;

import lombok.Builder;

@Builder
public record NewCabinetDto (int number,
                             String name
                             ) {

}


