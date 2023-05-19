package io.github.cokelee777.springsecurityjwtauth;

import io.github.cokelee777.springsecurityjwtauth.domain.MemoryUser;
import io.github.cokelee777.springsecurityjwtauth.repository.MemoryUserRepository;
import io.github.cokelee777.springsecurityjwtauth.repository.UserRepository;
import io.github.cokelee777.springsecurityjwtauth.security.handler.failure.CustomAuthenticationFailureHandler;
import io.github.cokelee777.springsecurityjwtauth.security.handler.failure.JwtAuthenticationFailureHandler;
import io.github.cokelee777.springsecurityjwtauth.security.handler.success.CustomAuthenticationSuccessHandler;
import io.github.cokelee777.springsecurityjwtauth.security.handler.success.JwtMemoryAuthenticationSuccessHandler;
import io.github.cokelee777.springsecurityjwtauth.security.service.JwtMemoryUserDetailsService;
import io.github.cokelee777.springsecurityjwtauth.security.service.PrincipalUserDetailsService;
import io.github.cokelee777.springsecurityjwtauth.security.token.creator.MemoryJwtTokenCreator;
import io.github.cokelee777.springsecurityjwtauth.security.token.creator.TokenCreator;
import io.github.cokelee777.springsecurityjwtauth.security.token.provider.MemoryJwtTokenProvider;
import io.github.cokelee777.springsecurityjwtauth.security.token.provider.TokenProvider;
import io.github.cokelee777.springsecurityjwtauth.security.token.service.MemoryJwtTokenService;
import io.github.cokelee777.springsecurityjwtauth.security.token.service.TokenService;
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

    @Bean
    @SuppressWarnings("unchecked")
    public <T extends TokenCreator> T tokenCreator() { return (T) new MemoryJwtTokenCreator(); }

    @Bean
    @SuppressWarnings("unchecked")
    public <T extends TokenProvider> T tokenProvider() { return (T) new MemoryJwtTokenProvider(tokenCreator()); }

    @Bean
    @SuppressWarnings("unchecked")
    public <T extends TokenService> T tokenService() { return (T) new MemoryJwtTokenService(tokenProvider()); }

    @Bean
    public CustomAuthenticationFailureHandler customAuthenticationFailureHandler() {
        return new JwtAuthenticationFailureHandler();
    }

    @Bean
    public CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new JwtMemoryAuthenticationSuccessHandler(tokenService());
    }
}
