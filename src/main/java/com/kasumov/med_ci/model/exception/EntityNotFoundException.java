package com.kasumov.med_ci.model.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * исключене кидаем если сущность не найдена по переданным параметрам
 */

@Getter
@Setter
public class EntityNotFoundException extends RuntimeException {
    private final int code;
    private final String message;

    public EntityNotFoundException(String message) {
        this.code = 450;
        this.message = message;
    }
}
