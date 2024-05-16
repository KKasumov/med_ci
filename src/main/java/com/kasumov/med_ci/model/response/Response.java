package com.kasumov.med_ci.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Response<T> {
    protected Boolean success;
    protected T data;
    protected int code;
    protected List<String> messages;

    public static <T> Response<T> ok() {
        return new SuccessResponse<>();
    }

    public static <T> Response<T> ok(T data) {
        return new SuccessResponse<>(data);
    }

    public static <T> Response<T> error(int code, String text) {
        return new ErrorResponse<>(code, text);
    }

    public static <T> Response<T> ok(int code, List<String> messages) {
        return new SuccessResponse<>(code, null, messages);
    }
}



