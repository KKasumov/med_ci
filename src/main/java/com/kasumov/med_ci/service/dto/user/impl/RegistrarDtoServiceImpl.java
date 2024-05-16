package com.kasumov.med_ci.service.dto.user.impl;

import com.kasumov.med_ci.model.dto.user.registrar.CurrentRegistrarDto;
import com.kasumov.med_ci.model.dto.user.registrar.converter.RegistrarDtoConverter;
import com.kasumov.med_ci.model.entity.user.Employee;
import com.kasumov.med_ci.service.dto.user.RegistrarDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrarDtoServiceImpl implements RegistrarDtoService {
    private final RegistrarDtoConverter registrarDtoConverter;

    @Override
    public CurrentRegistrarDto getCurrentRegistrarDto(Employee registrar) {
        return registrarDtoConverter.entityToCurrentRegistrarDto(registrar);
    }
}
