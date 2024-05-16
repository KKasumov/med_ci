package com.kasumov.med_ci.model.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * исключение кидаем если в поле уже записан передаваемый id
 */

@Getter
@Setter
public class SameConstraintException extends RuntimeException {
    private final int code;
    private final String message;

    public SameConstraintException(String message) {
        this.code = 455;
        this.message = message;
    }

}
