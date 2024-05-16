package com.kasumov.med_ci.model.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * исключение кидаем если какая-то дата просрочена
 */

@Getter
@Setter
public class OverdueDateException extends RuntimeException {
    private final int code;
    private final String message;

    public OverdueDateException(String message) {
        this.code = 452;
        this.message = message;
    }
}
