package com.kasumov.med_ci.model.dto.user.laborContract;

import lombok.Builder;

@Builder
public record LaborUserContractDto(long id,
                                   String endDate,
                                   String firstName,
                                   String lastName,
                                   String patronymic,
                                   String position) {
}
