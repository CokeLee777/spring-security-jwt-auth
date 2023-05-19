package io.github.cokelee777.springsecurityjwtauth.security.handler.success;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public interface CustomAuthenticationSuccessHandler extends AuthenticationSuccessHandler {
    void setDefaultResponse(HttpServletResponse response) throws IOException;
}
