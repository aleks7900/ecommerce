package com.aleks.gateway.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtRoleConverter jwtRoleConverter;

  private final JwtAuthenticationEntryPoint authenticationEntryPoint;

  private final JwtAccessDeniedHandler accessDeniedHandler;

  @Bean
  public SecurityWebFilterChain securityWebFilterChain(
      ServerHttpSecurity http
  ) {

    return http

        .csrf(ServerHttpSecurity.CsrfSpec::disable)

        .authorizeExchange(exchange -> exchange

            .pathMatchers(
                "/api/auth/**",
                "/actuator/**",
                "/api/products/**",
                "/swagger-ui/**",
                "/v3/api-docs/**"
            ).permitAll()

            .pathMatchers("/api/admin/**")
            .hasRole("ADMIN")

            .anyExchange()
            .authenticated()
        )

        .exceptionHandling(exceptionHandling -> exceptionHandling

            .authenticationEntryPoint(authenticationEntryPoint)

            .accessDeniedHandler(accessDeniedHandler)
        )

        .oauth2ResourceServer(oauth2 -> oauth2

            .jwt(jwt -> jwt.jwtAuthenticationConverter(
                new ReactiveJwtAuthenticationConverterAdapter(
                    jwtRoleConverter
                )
            ))
        )

        .build();
  }
}