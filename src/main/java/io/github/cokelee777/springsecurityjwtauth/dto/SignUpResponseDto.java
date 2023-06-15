package io.github.cokelee777.springsecurityjwtauth.dto;

import io.github.cokelee777.springsecurityjwtauth.entity.User;
import io.github.cokelee777.springsecurityjwtauth.enums.UserRole;

public record SignUpResponseDto(Object id, String identifier, String nickname, UserRole role) {

    public static SignUpResponseDto fromUser(User user) {
        return new SignUpResponseDto(user.getId(), user.getIdentifier(), user.getNickname(), user.getRole());
    }
}
