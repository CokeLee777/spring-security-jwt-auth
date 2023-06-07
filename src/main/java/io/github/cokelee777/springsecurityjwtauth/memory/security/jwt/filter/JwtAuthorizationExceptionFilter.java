package io.github.cokelee777.springsecurityjwtauth.memory.security.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cokelee777.springsecurityjwtauth.common.dto.common.ExceptionResponseBody;
import io.github.cokelee777.springsecurityjwtauth.common.utils.DefaultHttpMessage;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.security.sasl.AuthenticationException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AccessDeniedException;

public class JwtAuthorizationExceptionFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException {
        try {
            filterChain.doFilter(request, response);
        } catch(AccessDeniedException e) {
            setErrorResponse(
                    HttpStatus.FORBIDDEN,
                    response,
                    DefaultHttpMessage.FORBIDDEN,
                    e.getMessage());
        } catch(ExpiredJwtException e) {
            setErrorResponse(
                    HttpStatus.MOVED_PERMANENTLY,
                    response,
                    String.format(DefaultHttpMessage.MOVED_PERMANENTLY, "/users/sign-in"),
                    e.getMessage());
        } catch(JwtException | AuthenticationException e) {
            setErrorResponse(HttpStatus.UNAUTHORIZED, response, DefaultHttpMessage.UNAUTHORIZED, e.getMessage());
        } catch(IllegalArgumentException e) {
            setErrorResponse(HttpStatus.BAD_REQUEST, response, DefaultHttpMessage.BAD_REQUEST, e.getMessage());
        } catch(Exception e) {
            setErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR, response, DefaultHttpMessage.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse response, String message, String details) throws IOException {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        objectMapper.writeValue(response.getWriter(),
                new ExceptionResponseBody(status.getReasonPhrase(), message, details));
    }
}
