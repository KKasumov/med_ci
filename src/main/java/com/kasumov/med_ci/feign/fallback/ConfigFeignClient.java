package com.kasumov.med_ci.feign.fallback;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class ConfigFeignClient implements FallbackFactory<FeignClientThrowable> {
    @Override
    public FeignClientThrowable create(Throwable cause) {
        return new FeignClientThrowable(cause);
    }
}
