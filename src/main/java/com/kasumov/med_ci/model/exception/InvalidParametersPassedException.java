package com.kasumov.med_ci.model.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * исключение кидаем если переданы неверные параметры
 */

@Getter
@Setter
public class InvalidParametersPassedException extends RuntimeException {
    private final int code;
    private final String message;

    public InvalidParametersPassedException(String message) {
        this.code = 453;
        this.message = message;
    }
}
