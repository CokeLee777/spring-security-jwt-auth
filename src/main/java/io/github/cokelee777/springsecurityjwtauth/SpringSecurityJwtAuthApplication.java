package io.github.cokelee777.springsecurityjwtauth;

import io.github.cokelee777.springsecurityjwtauth.entity.MemoryUser;
import io.github.cokelee777.springsecurityjwtauth.repository.DBUserRepository;
import io.github.cokelee777.springsecurityjwtauth.repository.JpaUserRepository;
import io.github.cokelee777.springsecurityjwtauth.repository.MemoryUserRepository;
import io.github.cokelee777.springsecurityjwtauth.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SpringBootApplication
public class SpringSecurityJwtAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityJwtAuthApplication.class, args);
    }

    /**
     * Choose data access layer
     */

    /*
    @Configuration
    static class MemoryConfiguration {

        @Bean
        public Map<String, MemoryUser> memoryStore() {
            return new ConcurrentHashMap<>();
        }

        @Bean
        public UserRepository userRepository() {
            return new MemoryUserRepository(memoryStore());
        }
    }
     */

    @Configuration
    static class DBConfiguration {

        private final JpaUserRepository jpaUserRepository;

        DBConfiguration(JpaUserRepository jpaUserRepository) {
            this.jpaUserRepository = jpaUserRepository;
        }

        @Bean
        public UserRepository userRepository() {
            return new DBUserRepository(jpaUserRepository);
        }
    }
}
