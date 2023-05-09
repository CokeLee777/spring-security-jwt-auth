package io.github.cokelee777.springsecurityjwtauth.security.service;

import io.github.cokelee777.springsecurityjwtauth.domain.User;

public interface JwtUserDetailsService<T extends User> extends PrincipalUserDetailsService {
}
