package com.kasumov.med_ci.model.dto.user.userHistory;

import com.kasumov.med_ci.model.dto.user.contact.ContactDto;
import lombok.Builder;

import java.util.Set;

@Builder
public record UserHistoryDto(Set<ContactDto> contacts) {
}
