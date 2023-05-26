package io.github.cokelee777.springsecurityjwtauth.security.config;

import io.github.cokelee777.springsecurityjwtauth.security.filter.JwtAuthenticationFilter;
import io.github.cokelee777.springsecurityjwtauth.security.filter.JwtAuthorizationExceptionFilter;
import io.github.cokelee777.springsecurityjwtauth.security.filter.JwtMemoryAuthorizationFilter;
import io.github.cokelee777.springsecurityjwtauth.security.handler.failure.CustomAccessDeniedHandler;
import io.github.cokelee777.springsecurityjwtauth.security.handler.failure.CustomAuthenticationFailureHandler;
import io.github.cokelee777.springsecurityjwtauth.security.handler.success.CustomAuthenticationSuccessHandler;
import io.github.cokelee777.springsecurityjwtauth.security.provider.JwtAuthenticationProvider;
import io.github.cokelee777.springsecurityjwtauth.security.service.PrincipalUserDetailsService;
import io.github.cokelee777.springsecurityjwtauth.security.token.service.TokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
public class JwtSecurityConfiguration {

    private static final String[] PUBLIC_END_POINT = {"/", "/users/sign-in", "/users/sign-up"};

    private final PrincipalUserDetailsService principalUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final TokenService tokenService;

    public JwtSecurityConfiguration(PrincipalUserDetailsService principalUserDetailsService,
                                    PasswordEncoder passwordEncoder,
                                    CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler,
                                    CustomAuthenticationFailureHandler customAuthenticationFailureHandler,
                                    CustomAccessDeniedHandler customAccessDeniedHandler,
                                    TokenService tokenService) {
        this.principalUserDetailsService = principalUserDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
        this.customAuthenticationFailureHandler = customAuthenticationFailureHandler;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
        this.tokenService = tokenService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        /*
         * REST API 서버를 기반으로 하기 때문에 CSRF 필터 해제, 폼 로그인 사용 X
         * JWT 토큰 기반의 인증, 인가를 위해 HTTP Basic 인증 사용 X, 세션 생성 및 사용 X
         */
        http.csrf().disable()
            .formLogin().disable()
            .httpBasic().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 인가 API
        http.authorizeHttpRequests()
            .requestMatchers(PUBLIC_END_POINT).permitAll()
            .requestMatchers("/users/**")
                .hasAnyRole("USER", "MANAGER", "ADMIN")
            .requestMatchers("/manager/**")
                .hasAnyRole("MANAGER", "ADMIN")
            .requestMatchers("/admin/**")
                .hasAnyRole("ADMIN")
            .anyRequest().authenticated();

        http.exceptionHandling()
                .accessDeniedHandler(customAccessDeniedHandler);

        // JWT DSL 추가
        http.apply(new JwtCustomDsl());

        return http.build();
    }

    // 공유객체에서 AuthenticationManager를 가져와 사용하기 위해 CustomDSL 정의
    public class JwtCustomDsl extends AbstractHttpConfigurer<JwtCustomDsl, HttpSecurity> {

        @Override
        public void configure(HttpSecurity http) {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager);
            jwtAuthenticationFilter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler);
            jwtAuthenticationFilter.setAuthenticationFailureHandler(customAuthenticationFailureHandler);
            http.addFilterAfter(jwtAuthenticationFilter, LogoutFilter.class)
                    .authenticationProvider(new JwtAuthenticationProvider(principalUserDetailsService, passwordEncoder));

            JwtMemoryAuthorizationFilter jwtMemoryAuthorizationFilter =
                    new JwtMemoryAuthorizationFilter(authenticationManager, tokenService);
            JwtAuthorizationExceptionFilter jwtAuthorizationExceptionFilter = new JwtAuthorizationExceptionFilter();
            http.addFilterBefore(jwtMemoryAuthorizationFilter, JwtAuthenticationFilter.class);
            http.addFilterBefore(jwtAuthorizationExceptionFilter, JwtMemoryAuthorizationFilter.class);
        }
    }
}
