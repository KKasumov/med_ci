package com.kasumov.med_ci.feign;

import com.kasumov.med_ci.feign.fallback.ConfigFeignClient;
import com.kasumov.med_ci.model.dto.testsystem.AuthenticationRequestDto;
import com.kasumov.med_ci.model.dto.testsystem.RequestPatientDtoTestSystem;
import com.kasumov.med_ci.model.dto.testsystem.ResponseJwtTokenTestSystem;
import com.kasumov.med_ci.model.dto.testsystem.ResponsePatientDtoTestSystem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "${app.feign.config.name}", url = "${app.feign.config.url}", fallbackFactory = ConfigFeignClient.class)
public interface TestSystemFeignClient {

    @PostMapping(value = "/api/auth/login")
    ResponseJwtTokenTestSystem getJwtTokenByTestSystem(@RequestBody AuthenticationRequestDto authenticationRequestDto);

    @PostMapping(value = "/checker/check/people")
    ResponsePatientDtoTestSystem checkPatientInTestSystem(
            @RequestHeader("Authorization") String token,
            @RequestBody RequestPatientDtoTestSystem requestPatientDtoTestSystem);
}
