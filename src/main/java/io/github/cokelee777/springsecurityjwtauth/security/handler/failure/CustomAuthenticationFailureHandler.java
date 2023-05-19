package io.github.cokelee777.springsecurityjwtauth.security.handler.failure;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

public interface CustomAuthenticationFailureHandler extends AuthenticationFailureHandler {
    void setErrorResponse(HttpStatus status, HttpServletResponse response,
                                 String message, String details) throws IOException;
}
