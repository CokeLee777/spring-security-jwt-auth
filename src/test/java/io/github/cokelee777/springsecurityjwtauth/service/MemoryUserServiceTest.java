package io.github.cokelee777.springsecurityjwtauth.service;

import io.github.cokelee777.springsecurityjwtauth.domain.MemoryUser;
import io.github.cokelee777.springsecurityjwtauth.dto.SignUpRequestDto;
import io.github.cokelee777.springsecurityjwtauth.exception.DuplicateIdentifierException;
import io.github.cokelee777.springsecurityjwtauth.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
class MemoryUserServiceTest {

    @Autowired
    private UserService memoryUserService;

    @Autowired
    private UserRepository<MemoryUser> memoryUserRepository;

    @Autowired
    private Map<String, MemoryUser> memoryStore;

    @AfterEach
    void afterEach() {
        memoryStore.clear();
    }

    @Test
    void createUserSuccess() {
        // given
        SignUpRequestDto signUpRequestDto =
                new SignUpRequestDto("user123@naver.com", "user123!", "user123");

        // when
        memoryUserService.createUser(signUpRequestDto);

        // then
        Assertions.assertThat(memoryUserRepository.existsByIdentifier(signUpRequestDto.identifier())).isTrue();
    }

    @Test
    void createUserDuplicateIdentifierException() {
        // given
        SignUpRequestDto signUpRequestDto =
                new SignUpRequestDto("user123@naver.com", "user123!", "user123");
        memoryUserService.createUser(signUpRequestDto);

        // when, then
        Assertions.assertThatThrownBy(() -> memoryUserService.createUser(signUpRequestDto))
                .isInstanceOf(DuplicateIdentifierException.class);
    }
}
