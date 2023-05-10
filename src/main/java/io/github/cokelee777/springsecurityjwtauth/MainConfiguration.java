package io.github.cokelee777.springsecurityjwtauth;

import io.github.cokelee777.springsecurityjwtauth.domain.MemoryUser;
import io.github.cokelee777.springsecurityjwtauth.repository.MemoryUserRepository;
import io.github.cokelee777.springsecurityjwtauth.repository.UserRepository;
import io.github.cokelee777.springsecurityjwtauth.security.service.JwtMemoryUserDetailsService;
import io.github.cokelee777.springsecurityjwtauth.security.service.PrincipalUserDetailsService;
import io.github.cokelee777.springsecurityjwtauth.service.MemoryUserService;
import io.github.cokelee777.springsecurityjwtauth.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class MainConfiguration {

    // 동시성 관리를 위해 스레드 안전한 ConcurrentHashMap 사용
    @Bean
    public Map<String, MemoryUser> memoryStore() {
        return new ConcurrentHashMap<>();
    }

    @Bean
    public UserRepository<MemoryUser> userRepository() {
        return new MemoryUserRepository(memoryStore());
    }

    @Bean
    public UserService userService() {
        return new MemoryUserService(userRepository(), passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PrincipalUserDetailsService jwtUserDetailsService() {
        return new JwtMemoryUserDetailsService(userRepository());
    }
}
