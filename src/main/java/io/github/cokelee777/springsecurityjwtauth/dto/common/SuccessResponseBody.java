package io.github.cokelee777.springsecurityjwtauth.dto.common;

import jakarta.annotation.Nullable;

public record SuccessResponseBody<T>(
        String code,
        String message,
        @Nullable T data
) {
}
