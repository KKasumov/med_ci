package com.kasumov.med_ci.model.dto.user.polis;

import com.kasumov.med_ci.model.enums.InsuranceType;
import lombok.Builder;

@Builder
public record PolisForResponseDto(String serialPolis,
                                  String numberPolis,
                                  String startOfPolis,
                                  String endOfPolis,
                                  InsuranceType insuranceType) {
}
