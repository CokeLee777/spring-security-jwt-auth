package io.github.cokelee777.springsecurityjwtauth.memory.security.jwt.config;

import io.github.cokelee777.springsecurityjwtauth.memory.security.jwt.filter.JwtAuthenticationFilter;
import io.github.cokelee777.springsecurityjwtauth.memory.security.jwt.filter.JwtAuthorizationExceptionFilter;
import io.github.cokelee777.springsecurityjwtauth.memory.security.jwt.filter.JwtMemoryAuthorizationFilter;
import io.github.cokelee777.springsecurityjwtauth.memory.security.jwt.handler.failure.JwtAccessDeniedHandler;
import io.github.cokelee777.springsecurityjwtauth.memory.security.jwt.handler.failure.JwtAuthenticationFailureHandler;
import io.github.cokelee777.springsecurityjwtauth.memory.security.jwt.handler.process.JwtLogoutHandler;
import io.github.cokelee777.springsecurityjwtauth.memory.security.jwt.handler.success.JwtLogoutSuccessHandler;
import io.github.cokelee777.springsecurityjwtauth.memory.security.jwt.handler.success.JwtMemoryAuthenticationSuccessHandler;
import io.github.cokelee777.springsecurityjwtauth.memory.security.jwt.provider.JwtAuthenticationProvider;
import io.github.cokelee777.springsecurityjwtauth.memory.security.jwt.token.service.JwtMemoryTokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
public class JwtSecurityConfiguration {

    private static final String LOGOUT_END_POINT = "/users/sign-out";
    private static final String[] PUBLIC_END_POINT = {"/"};
    private static final String[] ANONYMOUS_END_POINT = {"/", "/users/sign-in", "/users/sign-up"};
    private final JwtMemoryAuthenticationSuccessHandler jwtMemoryAuthenticationSuccessHandler;
    private final JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtMemoryTokenService jwtMemoryTokenService;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final JwtLogoutHandler jwtLogoutHandler;
    private final JwtLogoutSuccessHandler jwtLogoutSuccessHandler;

    public JwtSecurityConfiguration(
            JwtMemoryAuthenticationSuccessHandler jwtMemoryAuthenticationSuccessHandler,
            JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler,
            JwtAccessDeniedHandler jwtAccessDeniedHandler,
            JwtMemoryTokenService jwtMemoryTokenService,
            JwtAuthenticationProvider jwtAuthenticationProvider,
            JwtLogoutHandler jwtLogoutHandler,
            JwtLogoutSuccessHandler jwtLogoutSuccessHandler) {
        this.jwtMemoryAuthenticationSuccessHandler = jwtMemoryAuthenticationSuccessHandler;
        this.jwtAuthenticationFailureHandler = jwtAuthenticationFailureHandler;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
        this.jwtMemoryTokenService = jwtMemoryTokenService;
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
        this.jwtLogoutHandler = jwtLogoutHandler;
        this.jwtLogoutSuccessHandler = jwtLogoutSuccessHandler;
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

        // 로그아웃 설정
        http.logout()
                .logoutUrl(LOGOUT_END_POINT)
                .addLogoutHandler(jwtLogoutHandler)
                .logoutSuccessHandler(jwtLogoutSuccessHandler);

        // 인가 API
        http.authorizeHttpRequests()
            .requestMatchers(PUBLIC_END_POINT).permitAll()
            .requestMatchers(ANONYMOUS_END_POINT).anonymous()
            .requestMatchers("/users/**")
                .hasAnyRole("USER", "MANAGER", "ADMIN")
            .requestMatchers("/manager/**")
                .hasAnyRole("MANAGER", "ADMIN")
            .requestMatchers("/admin/**")
                .hasAnyRole("ADMIN")
            .anyRequest().authenticated();

        http.exceptionHandling()
                .accessDeniedHandler(jwtAccessDeniedHandler);

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
            jwtAuthenticationFilter.setAuthenticationSuccessHandler(jwtMemoryAuthenticationSuccessHandler);
            jwtAuthenticationFilter.setAuthenticationFailureHandler(jwtAuthenticationFailureHandler);
            http.addFilterAfter(jwtAuthenticationFilter, LogoutFilter.class)
                    .authenticationProvider(jwtAuthenticationProvider);

            JwtMemoryAuthorizationFilter jwtMemoryAuthorizationFilter =
                    new JwtMemoryAuthorizationFilter(authenticationManager, jwtMemoryTokenService);
            JwtAuthorizationExceptionFilter jwtAuthorizationExceptionFilter = new JwtAuthorizationExceptionFilter();
            http.addFilterBefore(jwtMemoryAuthorizationFilter, JwtAuthenticationFilter.class);
            http.addFilterBefore(jwtAuthorizationExceptionFilter, JwtMemoryAuthorizationFilter.class);
        }
    }
}
