package io.github.cokelee777.springsecurityjwtauth.common.dto.common;

public record ExceptionResponseBody(
        String code,
        String message,
        String detail
) {
}
