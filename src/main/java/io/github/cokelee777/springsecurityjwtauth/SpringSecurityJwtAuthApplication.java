package io.github.cokelee777.springsecurityjwtauth;

import io.github.cokelee777.springsecurityjwtauth.domain.MemoryUser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SpringBootApplication
public class SpringSecurityJwtAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityJwtAuthApplication.class, args);
    }

    // 동시성 관리를 위해 스레드 안전한 ConcurrentHashMap 사용
    @Bean
    public Map<String, MemoryUser> memoryStore() {
        return new ConcurrentHashMap<>();
    }
}
