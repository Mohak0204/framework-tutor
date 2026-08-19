package com.frameworktutor.backend.common.api;

import java.time.Instant;
import java.util.List;

public record ApiErrorResponse(
        Instant timestamp,
        int status,
        String error,
        String message,
        String path,
        List<ApiFieldError> fieldErrors) {

    public ApiErrorResponse {
        fieldErrors = List.copyOf(fieldErrors);
    }
}
