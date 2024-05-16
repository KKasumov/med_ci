package com.kasumov.med_ci.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SuccessResponse<T> extends Response<T> {
    public SuccessResponse(T data) {
        this.code = 200;
        this.success = true;
        this.data = data;
    }

    public SuccessResponse() {
        this.code = 200;
        this.success = true;
    }

    public SuccessResponse(int code, T data, List<String> messages) {
        this.success = true;
        this.code = code;
        this.data = data;
        this.messages = messages;
    }
}
