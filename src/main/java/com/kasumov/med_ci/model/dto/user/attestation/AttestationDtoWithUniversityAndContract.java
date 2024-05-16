package com.kasumov.med_ci.model.dto.user.attestation;

import com.kasumov.med_ci.model.dto.user.laborContract.LaborContractDto;
import com.kasumov.med_ci.model.dto.user.university.UniversityDto;
import lombok.Builder;

@Builder
public record AttestationDtoWithUniversityAndContract(long id,
                                                     String dateFrom,
                                                     String dateTo,
                                                     String number,
                                                     UniversityDto universityDto,
                                                     LaborContractDto laborContractDto) {
}
