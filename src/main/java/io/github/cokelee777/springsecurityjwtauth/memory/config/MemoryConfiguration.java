package io.github.cokelee777.springsecurityjwtauth.memory.config;

import io.github.cokelee777.springsecurityjwtauth.memory.entity.MemoryUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class MemoryConfiguration {

    @Bean
    public Map<String, MemoryUser> memoryStore() {
        return new ConcurrentHashMap<>();
    }
}
