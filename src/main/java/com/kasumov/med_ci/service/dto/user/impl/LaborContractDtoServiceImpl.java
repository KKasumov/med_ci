package com.kasumov.med_ci.service.dto.user.impl;

import com.kasumov.med_ci.model.dto.user.laborContract.LaborUserContractDto;
import com.kasumov.med_ci.model.dto.user.laborContract.converter.LaborContractDtoConverter;
import com.kasumov.med_ci.util.MessageService;
import com.kasumov.med_ci.model.entity.user.items.LaborContract;
import com.kasumov.med_ci.service.dto.user.LaborContractDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class LaborContractDtoServiceImpl implements LaborContractDtoService {

    private final LaborContractDtoConverter laborContractDtoConverter;

    private final MessageService messageService;


    @Transactional
    @Override
    public LaborUserContractDto terminationLaborContract(LaborContract laborContract, LocalDate endDate) {
        laborContract.setEndDate(endDate);
        messageService.sendEmailEmployeeTerminationLaborContact(laborContract);
        return laborContractDtoConverter.laborContractUserToDto(laborContract);
    }
}
