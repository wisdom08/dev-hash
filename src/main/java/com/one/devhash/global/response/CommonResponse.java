package com.one.devhash.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

public record CommonResponse<T>(int code, boolean success, @JsonInclude(JsonInclude.Include.NON_NULL) T result) {
    @Builder
    public CommonResponse(int code, boolean success, T result) {
        this.code = code;
        this.success = success;
        this.result = result;
    }
}
