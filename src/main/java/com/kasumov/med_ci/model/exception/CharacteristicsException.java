package com.kasumov.med_ci.model.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CharacteristicsException extends RuntimeException {

    private final int code;
    private final String message;

    public CharacteristicsException(String message) {
        this.code = 456;
        this.message = message;
    }
}
