package com.kasumov.med_ci.service.dto.user.impl;


import com.kasumov.med_ci.model.dto.user.economist.CurrentEconomistDto;
import com.kasumov.med_ci.model.dto.user.economist.converter.EconomistDtoConverter;
import com.kasumov.med_ci.model.entity.user.Employee;
import com.kasumov.med_ci.service.dto.user.EconomistDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EconomistDtoServiceImpl implements EconomistDtoService {

    private final EconomistDtoConverter economistDtoConverter;

    @Override
    public CurrentEconomistDto getCurrentEconomistDto(Employee economist) {
        return economistDtoConverter.entityToCurrentEconomistDto(economist);
    }
}
