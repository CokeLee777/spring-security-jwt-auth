package io.github.cokelee777.springsecurityjwtauth.service;

import io.github.cokelee777.springsecurityjwtauth.common.dto.SignUpRequestDto;
import io.github.cokelee777.springsecurityjwtauth.common.exception.DuplicateIdentifierException;
import io.github.cokelee777.springsecurityjwtauth.memory.entity.MemoryUser;
import io.github.cokelee777.springsecurityjwtauth.memory.repository.MemoryUserRepository;
import io.github.cokelee777.springsecurityjwtauth.memory.service.MemoryUserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
class MemoryUserServiceTest {

    @Autowired
    private MemoryUserService memoryUserService;

    @Autowired
    private MemoryUserRepository memoryUserRepository;

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
