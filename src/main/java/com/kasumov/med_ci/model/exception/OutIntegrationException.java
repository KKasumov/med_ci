package com.kasumov.med_ci.model.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * исключение кидаем при ошибках связанных с интеграциями
 */

@Getter
@Setter
public class OutIntegrationException extends RuntimeException{
    private final int code;
    private final String message;

    public OutIntegrationException(String message) {
        this.code = 457;
        this.message = message;
    }
}
