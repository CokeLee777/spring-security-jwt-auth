package io.github.cokelee777.springsecurityjwtauth.common.dto.common;

import jakarta.annotation.Nullable;

public record SuccessResponseBody<T>(
        String code,
        String message,
        @Nullable T data
) {
}
