package io.github.cokelee777.springsecurityjwtauth.security.handler.failure;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cokelee777.springsecurityjwtauth.dto.common.ExceptionResponseBody;
import io.github.cokelee777.springsecurityjwtauth.exception.DuplicatedAuthenticationException;
import io.github.cokelee777.springsecurityjwtauth.utils.DefaultHttpMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class JwtAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        String message = exception.getMessage();
        if(exception instanceof DuplicatedAuthenticationException) {
            setErrorResponse(HttpStatus.FORBIDDEN, response, DefaultHttpMessage.FORBIDDEN, message);
        } else if (exception instanceof AuthenticationServiceException
                || exception instanceof BadCredentialsException
                || exception instanceof UsernameNotFoundException) {
            setErrorResponse(HttpStatus.UNAUTHORIZED, response, DefaultHttpMessage.UNAUTHORIZED, message);
        } else {
            setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, response,
                    DefaultHttpMessage.INTERNAL_SERVER_ERROR, message);
        }
    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse response,
                                 String message, String details) throws IOException {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        objectMapper.writeValue(response.getWriter(),
                new ExceptionResponseBody(status.getReasonPhrase(), message, details));
    }
}
