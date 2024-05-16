package com.kasumov.med_ci.feign.fallback;

import com.kasumov.med_ci.feign.TestSystemFeignClient;
import com.kasumov.med_ci.model.dto.testsystem.AuthenticationRequestDto;
import com.kasumov.med_ci.model.dto.testsystem.RequestPatientDtoTestSystem;
import com.kasumov.med_ci.model.dto.testsystem.ResponseJwtTokenTestSystem;
import com.kasumov.med_ci.model.dto.testsystem.ResponsePatientDtoTestSystem;
import com.kasumov.med_ci.model.exception.FeignClientException;

public record FeignClientThrowable(Throwable cause) implements TestSystemFeignClient {
    @Override
    public ResponseJwtTokenTestSystem getJwtTokenByTestSystem(AuthenticationRequestDto authenticationRequestDto) {
        throw new FeignClientException("Сервис временно недоступен. Причина -> %s"
                .formatted(cause.getMessage()), cause);
    }

    @Override
    public ResponsePatientDtoTestSystem checkPatientInTestSystem(String header, RequestPatientDtoTestSystem requestPatientDtoTestSystem) {
        throw new FeignClientException("Сервис временно недоступен. Причина -> %s"
                .formatted(cause.getMessage()), cause);
    }
}
