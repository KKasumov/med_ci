package com.kasumov.med_ci.service.dto.testsystem.impl;

import com.kasumov.med_ci.feign.TestSystemFeignClient;
import com.kasumov.med_ci.util.DateConvertor;
import com.kasumov.med_ci.model.dto.testsystem.AuthenticationRequestDto;
import com.kasumov.med_ci.model.dto.testsystem.RequestPatientDtoTestSystem;
import com.kasumov.med_ci.model.dto.testsystem.ResponseJwtTokenTestSystem;
import com.kasumov.med_ci.model.dto.testsystem.ResponsePatientDtoTestSystem;
import com.kasumov.med_ci.service.dto.testsystem.TestSystemIntegration;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Setter
@Getter
@RequiredArgsConstructor
public class TestSystemIntegrationImpl implements TestSystemIntegration {

    private final DateConvertor dateConvertor;
    private final TestSystemFeignClient testSystemFeignClient;

    @Value("${testSystem.property.login}")
    private String emailTestSystem;
    @Value("${testSystem.property.password}")
    private String passwordTestSystem;

    private ResponseJwtTokenTestSystem responseJwtTokenTestSystem;

    @Override
    public ResponsePatientDtoTestSystem checkTokenAndPatientInTestSystem(RequestPatientDtoTestSystem requestPatientDto) {
        return testSystemFeignClient.checkPatientInTestSystem(checkTheTokenIsAliveAndGetJwt().jwt(), requestPatientDto);
    }

    private ResponseJwtTokenTestSystem checkTheTokenIsAliveAndGetJwt() {
        if (responseJwtTokenTestSystem == null ||
            responseJwtTokenTestSystem.expirationDate().isEmpty() ||
            dateConvertor.stringToLocalDateTime(responseJwtTokenTestSystem.expirationDate()).plusMinutes(3)
                    .isBefore(LocalDateTime.now())) {

            responseJwtTokenTestSystem = testSystemFeignClient.getJwtTokenByTestSystem(setAuthenticationRequestDto(
                    emailTestSystem, passwordTestSystem));

        }
        return responseJwtTokenTestSystem;
    }

    private AuthenticationRequestDto setAuthenticationRequestDto(String email, String password) {
        return new AuthenticationRequestDto(email, password);
    }
}
