package com.kasumov.med_ci.service.dto.testsystem;

import com.kasumov.med_ci.model.dto.testsystem.RequestPatientDtoTestSystem;
import com.kasumov.med_ci.model.dto.testsystem.ResponsePatientDtoTestSystem;

public interface TestSystemIntegration {
    ResponsePatientDtoTestSystem checkTokenAndPatientInTestSystem(RequestPatientDtoTestSystem requestPatientDto);
}
