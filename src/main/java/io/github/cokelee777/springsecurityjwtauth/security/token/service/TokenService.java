package io.github.cokelee777.springsecurityjwtauth.security.token.service;

import io.github.cokelee777.springsecurityjwtauth.security.token.domain.RefreshToken;
import org.springframework.security.core.userdetails.UserDetails;

public interface TokenService <U extends UserDetails, T, R extends RefreshToken>{

    T issue(U user);

    R reIssue(U user);

}
