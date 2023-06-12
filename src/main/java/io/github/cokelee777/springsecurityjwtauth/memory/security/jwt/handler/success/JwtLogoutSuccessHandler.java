package io.github.cokelee777.springsecurityjwtauth.memory.security.jwt.handler.success;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cokelee777.springsecurityjwtauth.common.dto.common.SuccessResponseBody;
import io.github.cokelee777.springsecurityjwtauth.common.utils.DefaultHttpMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class JwtLogoutSuccessHandler implements LogoutSuccessHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final static String LOGOUT_REDIRECT_END_POINT = "/users/sign-in";

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        response.setStatus(HttpStatus.MOVED_PERMANENTLY.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());

        objectMapper.writeValue(response.getWriter(), new SuccessResponseBody<>(
                HttpStatus.MOVED_PERMANENTLY.getReasonPhrase(),
                String.format(DefaultHttpMessage.MOVED_PERMANENTLY, LOGOUT_REDIRECT_END_POINT),
                null));
    }
}
