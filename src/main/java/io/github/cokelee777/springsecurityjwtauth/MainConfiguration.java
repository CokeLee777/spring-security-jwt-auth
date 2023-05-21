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
    @SuppressWarnings("unchecked")
    public <T extends UserRepository<MemoryUser>> T userRepository() {
        return (T) new MemoryUserRepository(memoryStore());
    }

    @Bean
    @SuppressWarnings("unchecked")
    public <T extends UserService> T userService() {
        return (T) new MemoryUserService(userRepository(), passwordEncoder());
    }

    @Bean
    @SuppressWarnings("unchecked")
    public <T extends PasswordEncoder> T passwordEncoder() {
        return (T) new BCryptPasswordEncoder();
    }

    @Bean
    @SuppressWarnings("unchecked")
    public <T extends PrincipalUserDetailsService> T principalUserDetailsService() {
        return (T) new JwtMemoryUserDetailsService(userRepository());
    }

    @Bean
    @SuppressWarnings("unchecked")
    public <T extends TokenCreator> T tokenCreator() {
        return (T) new MemoryJwtTokenCreator();
    }

    @Bean
    @SuppressWarnings("unchecked")
    public <T extends TokenProvider> T tokenProvider() {
        return (T) new MemoryJwtTokenProvider(tokenCreator());
    }

    @Bean
    @SuppressWarnings("unchecked")
    public <T extends TokenService> T tokenService() {
        return (T) new MemoryJwtTokenService(tokenProvider());
    }

    @Bean
    @SuppressWarnings("unchecked")
    public <T extends CustomAuthenticationFailureHandler> T customAuthenticationFailureHandler() {
        return (T) new JwtAuthenticationFailureHandler();
    }

    @Bean
    @SuppressWarnings("unchecked")
    public <T extends CustomAuthenticationSuccessHandler> T customAuthenticationSuccessHandler() {
        return (T) new JwtMemoryAuthenticationSuccessHandler(tokenService());
    }
}
