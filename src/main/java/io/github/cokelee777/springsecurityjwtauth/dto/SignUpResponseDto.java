package io.github.cokelee777.springsecurityjwtauth.dto;

import io.github.cokelee777.springsecurityjwtauth.enums.UserRole;

public record SignUpResponseDto(Object id, String identifier, String nickname, UserRole role) {
}
