package com.kasumov.med_ci.model.dto.user.patient;

import com.kasumov.med_ci.model.dto.user.contact.ContactDto;
import lombok.Builder;

import java.util.List;


@Builder
public record PatientContactDto(
    String email,
    List<ContactDto> contacts) {
}
