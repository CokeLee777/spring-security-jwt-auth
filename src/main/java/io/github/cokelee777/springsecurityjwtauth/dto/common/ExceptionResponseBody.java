package io.github.cokelee777.springsecurityjwtauth.dto.common;

public record ExceptionResponseBody(
        String code,
        String message,
        String detail
) {
}
