package com.kasumov.med_ci.model.dto.user.contact;

import com.kasumov.med_ci.model.enums.ContactType;
import lombok.Builder;

@Builder
public record ContactDto(long id,
                         ContactType contactType,
                         String value) {
}
