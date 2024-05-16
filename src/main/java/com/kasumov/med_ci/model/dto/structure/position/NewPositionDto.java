package com.kasumov.med_ci.model.dto.structure.position;

import com.kasumov.med_ci.model.dto.structure.wage.NewWageDto;
import lombok.Builder;
import org.springframework.lang.Nullable;

import java.util.List;

@Builder
public record NewPositionDto(String name,
                             int daysForVocation,
                             @Nullable Long cabinetId,
                             @Nullable List<Long> equipmentsId,
                             NewWageDto wage) {
}
