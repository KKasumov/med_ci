package com.kasumov.med_ci.model.dto.user.employee;

import com.kasumov.med_ci.model.dto.user.identityDocument.NewIdentityDocumentDto;
import com.kasumov.med_ci.model.dto.user.laborContract.LaborContractForHiringDto;
import com.kasumov.med_ci.model.dto.user.contact.ContactEmployeeDto;
import com.kasumov.med_ci.model.dto.user.diploma.DiplomaForHiringDto;
import lombok.Builder;
import java.util.List;

@Builder
public record EmployeeForHiringDTO(String email,
                                   String snils,
                                   NewIdentityDocumentDto identityDocumentDto,
                                   LaborContractForHiringDto laborContractForHiringDto,
                                   Long roleId,
                                   Long positionId,
                                   DiplomaForHiringDto diplomaForHiringDto,
                                   List<ContactEmployeeDto> contactDtos) {
}
