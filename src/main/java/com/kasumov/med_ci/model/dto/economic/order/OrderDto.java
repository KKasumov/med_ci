package com.kasumov.med_ci.model.dto.economic.order;


import com.kasumov.med_ci.model.enums.InsuranceType;
import com.kasumov.med_ci.model.dto.economic.smo.SmoDto;
import lombok.Builder;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;



@Builder
public record OrderDto(long id,
                       InsuranceType insuranceType,
                       @Nullable String comment,
                       String date,
                       @Nullable BigDecimal money,
                       boolean isFormed,
                       boolean isAcceptedForPayment,
                       boolean isPayed,
                       SmoDto smo) {
}

