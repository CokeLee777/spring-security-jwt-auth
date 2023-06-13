package io.github.cokelee777.springsecurityjwtauth.entity;

import io.github.cokelee777.springsecurityjwtauth.enums.UserRole;

public interface User {

    Object getId();

    String getIdentifier();

    String getPassword();

    String getNickname();

    UserRole getRole();
}
