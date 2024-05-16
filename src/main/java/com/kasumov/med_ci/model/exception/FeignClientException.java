package com.kasumov.med_ci.model.exception;

public class FeignClientException extends RuntimeException{

    public FeignClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
