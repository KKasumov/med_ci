package com.kasumov.med_ci.model.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * исключение кидаем если занято какое-либо уникальное поле
 */

@Getter
@Setter
public class BusyConstraintException extends RuntimeException {
    private final int code;
    private final String message;

    public BusyConstraintException(String message) {
        this.code = 451;
        this.message = message;
    }
}
