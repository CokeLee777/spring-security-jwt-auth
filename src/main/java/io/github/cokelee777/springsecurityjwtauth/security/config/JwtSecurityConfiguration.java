package io.github.cokelee777.springsecurityjwtauth.security.config;

import io.github.cokelee777.springsecurityjwtauth.security.filter.JwtAuthenticationFilter;
import io.github.cokelee777.springsecurityjwtauth.security.filter.JwtAuthorizationExceptionFilter;
import io.github.cokelee777.springsecurityjwtauth.security.filter.JwtAuthorizationFilter;
import io.github.cokelee777.springsecurityjwtauth.security.handler.failure.JwtAccessDeniedHandler;
import io.github.cokelee777.springsecurityjwtauth.security.handler.failure.JwtAuthenticationFailureHandler;
import io.github.cokelee777.springsecurityjwtauth.security.handler.process.JwtLogoutHandler;
import io.github.cokelee777.springsecurityjwtauth.security.handler.success.JwtAuthenticationSuccessHandler;
import io.github.cokelee777.springsecurityjwtauth.security.handler.success.JwtLogoutSuccessHandler;
import io.github.cokelee777.springsecurityjwtauth.security.provider.JwtAuthenticationProvider;
import io.github.cokelee777.springsecurityjwtauth.security.token.service.JwtAccessTokenService;
import io.github.cokelee777.springsecurityjwtauth.security.token.service.JwtTokenDecryptService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class JwtSecurityConfiguration {

    private static final String LOGOUT_END_POINT = "/users/sign-out";
    private static final String[] PUBLIC_END_POINT = {"/"};
    private static final String[] ANONYMOUS_END_POINT = {"/", "/users/sign-in", "/users/sign-up"};
    private final JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler;
    private final JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAccessTokenService jwtAccessTokenService;
    private final JwtTokenDecryptService jwtTokenDecryptService;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final JwtLogoutHandler jwtLogoutHandler;
    private final JwtLogoutSuccessHandler jwtLogoutSuccessHandler;

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
            jwtAuthenticationFilter.setAuthenticationSuccessHandler(jwtAuthenticationSuccessHandler);
            jwtAuthenticationFilter.setAuthenticationFailureHandler(jwtAuthenticationFailureHandler);
            http.addFilterAfter(jwtAuthenticationFilter, LogoutFilter.class)
                    .authenticationProvider(jwtAuthenticationProvider);

            JwtAuthorizationFilter jwtMemoryAuthorizationFilter =
                    new JwtAuthorizationFilter(authenticationManager, jwtAccessTokenService, jwtTokenDecryptService);
            JwtAuthorizationExceptionFilter jwtAuthorizationExceptionFilter = new JwtAuthorizationExceptionFilter();
            http.addFilterBefore(jwtMemoryAuthorizationFilter, JwtAuthenticationFilter.class);
            http.addFilterBefore(jwtAuthorizationExceptionFilter, JwtAuthorizationFilter.class);
        }
    }
}
