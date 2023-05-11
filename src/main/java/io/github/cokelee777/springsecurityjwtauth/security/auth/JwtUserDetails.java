package io.github.cokelee777.springsecurityjwtauth.security.auth;

import io.github.cokelee777.springsecurityjwtauth.domain.User;

public interface JwtUserDetails<T extends User> extends PrincipalUserDetails {
}
