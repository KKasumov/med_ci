package com.kasumov.med_ci.service.dto.user;

import com.kasumov.med_ci.model.dto.user.laborContract.LaborUserContractDto;
import com.kasumov.med_ci.model.entity.user.items.LaborContract;

import java.time.LocalDate;

public interface LaborContractDtoService {

    LaborUserContractDto terminationLaborContract(LaborContract laborContract, LocalDate endDate);

}
