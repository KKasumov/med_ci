package com.kasumov.med_ci.model.dto.user.diploma;

import com.kasumov.med_ci.model.dto.user.laborContract.LaborContractDto;
import com.kasumov.med_ci.model.dto.user.university.UniversityDto;
import lombok.Builder;

@Builder
public record DiplomaDtoWithUniversityAndContract(long id,
                                                      String endDate,
                                                      String number,
                                                      UniversityDto universityDto,
                                                      LaborContractDto laborContractDto) {
}
