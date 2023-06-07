package io.github.cokelee777.springsecurityjwtauth.memory.security.jwt.handler.failure;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cokelee777.springsecurityjwtauth.common.dto.common.ExceptionResponseBody;
import io.github.cokelee777.springsecurityjwtauth.common.utils.DefaultHttpMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        setErrorResponse(HttpStatus.FORBIDDEN, response, DefaultHttpMessage.FORBIDDEN, accessDeniedException.getMessage());
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
