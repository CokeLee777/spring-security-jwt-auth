package io.github.cokelee777.springsecurityjwtauth.repository;

import io.github.cokelee777.springsecurityjwtauth.domain.MemoryUser;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
class MemoryUserRepositoryTest {

    @Autowired
    private UserRepository<MemoryUser> memoryUserRepository;

    @Autowired
    private Map<String, MemoryUser> memoryStore;

    @AfterEach
    void afterEach() {
        memoryStore.clear();
    }

    @Test
    void save() {
        // given
        MemoryUser memoryUser = MemoryUser.builder()
                .identifier("user123@naver.com")
                .password("user123!")
                .nickname("testuser")
                .build();

        // when
        memoryUserRepository.save(memoryUser);

        // then
        Assertions.assertThat(memoryStore.containsValue(memoryUser)).isTrue();
    }

    @Test
    void existsByIdentifier() {
        // given
        MemoryUser memoryUser = MemoryUser.builder()
                .identifier("existUser123@naver.com")
                .password("existUser123!")
                .nickname("existUser")
                .build();
        memoryUserRepository.save(memoryUser);

        // when
        boolean isExists = memoryUserRepository.existsByIdentifier("existUser123@naver.com");
        boolean isNotExists = memoryUserRepository.existsByIdentifier("notExistUser123@naver.com");

        //then
        Assertions.assertThat(isExists).isTrue();
        Assertions.assertThat(isNotExists).isFalse();
    }
}
