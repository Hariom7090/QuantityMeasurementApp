package com.app.quantitymeasurement.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    private final OAuth2LoginSuccessHandler
            oAuth2LoginSuccessHandler;

    public SecurityConfig(
            OAuth2LoginSuccessHandler
                    oAuth2LoginSuccessHandler
    ) {
        this.oAuth2LoginSuccessHandler =
                oAuth2LoginSuccessHandler;
    }

    @Bean
    public SecurityFilterChain
    securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http

                // Disable CSRF
                .csrf(csrf ->
                        csrf.disable()
                )

                // Allow H2 Console
                .headers(headers ->
                        headers.frameOptions(
                                frame ->
                                        frame.disable()
                        )
                )

                // Endpoint Security
                .authorizeHttpRequests(auth -> auth

                        // Public Endpoints
                        .requestMatchers(
                                "/api/auth/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/h2-console/**",
                                "/actuator/health"
                        ).permitAll()

                        // Protected APIs
                        .requestMatchers(
                                "/api/v1/quantities/**"
                        ).authenticated()

                        .anyRequest()
                        .authenticated()
                )

                // Google OAuth Login
                .oauth2Login(oauth2 ->
                        oauth2
                                .successHandler(
                                        oAuth2LoginSuccessHandler
                                )
                )

                // JWT Authentication
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(
                                Customizer.withDefaults()
                        )
                )

                // Exception Handling
                .exceptionHandling(exception ->
                        exception
                                .authenticationEntryPoint(
                                        (request,
                                         response,
                                         authException) -> {

                                            response.sendError(
                                                    HttpServletResponse.SC_UNAUTHORIZED,
                                                    "Unauthorized"
                                            );
                                        }
                                )
                );

        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {

        SecretKeySpec secretKey =
                new SecretKeySpec(
                        jwtSecret.getBytes(),
                        "HmacSHA256"
                );

        return NimbusJwtDecoder
                .withSecretKey(secretKey)
                .build();
    }
}