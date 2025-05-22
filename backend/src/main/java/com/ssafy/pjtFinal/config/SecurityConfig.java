package com.ssafy.pjtFinal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // REST API는 CSRF 비활성화
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                )

                .formLogin(form -> form.disable()) // 로그인 form 끄기
                .httpBasic(basic -> basic.disable()) // HTTP Basic 끄기
                .sessionManagement(session -> session
                        .maximumSessions(1) // 세션 하나만 유지
                );

        return http.build();
    }
}

