package com.kasumov.med_ci.service.dto.user;

import com.kasumov.med_ci.model.dto.user.registrar.CurrentRegistrarDto;
import com.kasumov.med_ci.model.entity.user.Employee;

public interface RegistrarDtoService {

    CurrentRegistrarDto getCurrentRegistrarDto(Employee registrar);
}
