package io.github.cokelee777.springsecurityjwtauth.repository;

import io.github.cokelee777.springsecurityjwtauth.common.repository.UserRepository;
import io.github.cokelee777.springsecurityjwtauth.memory.entity.MemoryUser;
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
        MemoryUser memoryUser = new MemoryUser(
                "user123@naver.com",
                "user123!",
                "testuser"
        );

        // when
        memoryUserRepository.save(memoryUser);

        // then
        Assertions.assertThat(memoryStore.containsValue(memoryUser)).isTrue();
    }

    @Test
    void existsByIdentifier() {
        // given
        MemoryUser memoryUser = new MemoryUser(
                "existUser123@naver.com",
                "existUser123!",
                "existUser"
        );
        memoryUserRepository.save(memoryUser);

        // when
        boolean isExists = memoryUserRepository.existsByIdentifier("existUser123@naver.com");
        boolean isNotExists = memoryUserRepository.existsByIdentifier("notExistUser123@naver.com");

        //then
        Assertions.assertThat(isExists).isTrue();
        Assertions.assertThat(isNotExists).isFalse();
    }
}
