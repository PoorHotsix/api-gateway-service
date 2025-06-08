package com.inkcloud.api_gateway_server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http){
        http
            .cors(withDefaults())
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
            .authorizeExchange(auth -> auth
                .pathMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll()
                .pathMatchers("/api/v1/members/signup/**", "/api/v1/members/login/**", "/api/v1/members/password/**").permitAll()
                .pathMatchers("/api/v1/reviews/products/**").permitAll() // 책별 리뷰 조회는 토큰 없이 허용
                .anyExchange().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
        return http.build();
    }
}
