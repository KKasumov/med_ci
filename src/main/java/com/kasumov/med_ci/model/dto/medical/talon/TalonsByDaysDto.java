package com.kasumov.med_ci.model.dto.medical.talon;

import lombok.Builder;

import java.util.List;

@Builder
public record TalonsByDaysDto(String day,
                              List<TalonDto> talons) {
}
