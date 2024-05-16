package com.kasumov.med_ci.model.dto.user.employee;

import com.kasumov.med_ci.model.dto.user.identityDocument.IdentityDocumentDto;
import com.kasumov.med_ci.model.dto.user.laborContract.LaborContractDto;
import com.kasumov.med_ci.model.dto.user.contact.ContactDto;
import lombok.Builder;
import org.springframework.lang.Nullable;

import java.util.List;

@Builder
public record EmployeeOfferDto(IdentityDocumentDto identityDocumentDto,
                               LaborContractDto laborContractDto,
                               @Nullable List<ContactDto> contactDto) {
}
