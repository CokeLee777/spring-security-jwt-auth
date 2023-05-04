package io.github.cokelee777.springsecurityjwtauth.domain;

import io.github.cokelee777.springsecurityjwtauth.enums.UserRole;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public abstract class User {

    private UserRole role = UserRole.USER;

    public User(UserRole role) {
        this.role = role;
    }
}
