package com.kasumov.med_ci.service.dto.user;


import com.kasumov.med_ci.model.dto.user.economist.CurrentEconomistDto;
import com.kasumov.med_ci.model.entity.user.Employee;

public interface EconomistDtoService {
    CurrentEconomistDto getCurrentEconomistDto(Employee economist);
}
