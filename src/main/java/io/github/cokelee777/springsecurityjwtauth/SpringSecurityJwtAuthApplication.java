package io.github.cokelee777.springsecurityjwtauth;

import io.github.cokelee777.springsecurityjwtauth.annotations.Memory;
import io.github.cokelee777.springsecurityjwtauth.entity.MemoryUser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SpringBootApplication
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Memory.class))
public class SpringSecurityJwtAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityJwtAuthApplication.class, args);
    }

    @Bean
    @Memory
    public Map<String, MemoryUser> memoryStore() {
        return new ConcurrentHashMap<>();
    }
}
