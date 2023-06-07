package io.github.cokelee777.springsecurityjwtauth.controller;

import io.github.cokelee777.springsecurityjwtauth.memory.entity.MemoryUser;
import io.github.cokelee777.springsecurityjwtauth.common.dto.SignUpRequestDto;
import io.github.cokelee777.springsecurityjwtauth.common.service.UserService;
import io.github.cokelee777.springsecurityjwtauth.common.utils.DefaultHttpMessage;
import jakarta.annotation.Nonnull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private Map<String, MemoryUser> memoryStore;

    private static final String EXISTS_USER_IDENTIFIER = "existsUser123@naver.com";

    @BeforeEach
    void beforeEach() {

        SignUpRequestDto signUpRequestDto = new SignUpRequestDto(
                EXISTS_USER_IDENTIFIER, "existsUser123!", "existsUser");
        userService.createUser(signUpRequestDto);
    }

    @AfterEach
    void afterEach() {
        memoryStore.clear();
    }

    private MvcResult postRequestMockServer(
            @Nonnull String path,
            @Nonnull HttpStatus status,
            @Nonnull String message,
            @Nonnull String requestBody) throws Exception {
        return mockMvc.perform(post(path)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody))
                .andExpect(status().is(status.value()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$..['code']").value(status.name()))
                .andExpect(jsonPath("$..['message']").value(message))
                .andReturn();
    }

    @Test
    void signUpSuccess() throws Exception {
        // given
        String requestBody = """
                {
                    "identifier": "user123@naver.com",
                    "password": "user123!"
                }""";

        // when, then
        postRequestMockServer("/sign-up", HttpStatus.OK, DefaultHttpMessage.OK, requestBody);
    }

    @Test
    void signUpFailByValidationException() throws Exception {
        // given
        String requestBody = """
                {
                    "identifier": "user123",
                    "password": "user123!"
                }""";

        // when, then
        postRequestMockServer("/sign-up", HttpStatus.BAD_REQUEST, DefaultHttpMessage.BAD_REQUEST, requestBody);
    }

    @Test
    void signUpFailBySyntaxException() throws Exception {
        // given
        String requestBody = """
                {
                    "identifier": "user123@naver.com",
                    "password": }""";

        // when, then
        postRequestMockServer("/sign-up", HttpStatus.BAD_REQUEST, DefaultHttpMessage.BAD_REQUEST, requestBody);
    }

    @Test
    void signUpFailByDuplicateIdentifierException() throws Exception {
        // given
        String requestBody = String.format("""
                {
                    "identifier": "%s",
                    "password": "user123!"
                }""", EXISTS_USER_IDENTIFIER);

        // when, then
        postRequestMockServer("/sign-up", HttpStatus.CONFLICT, DefaultHttpMessage.CONFLICT, requestBody);
    }
}
