package com.hongwei.factcheck.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // Providing our own security config
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Disable csrf because building stateless REST API
        http.csrf(csrf -> csrf.disable())

                // No sessions for now
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // Authorisation rules
                .authorizeHttpRequests(auth ->
                        // Allow anyone to call register endpoint
                        auth.requestMatchers("/api/auth/register").permitAll()
                                // Allow anything
                                .anyRequest().permitAll()
                )

                // Disable form login and HTTP basic UI
                .httpBasic(httpBasic -> {})   // keep basic enabled or:
                .formLogin(form -> form.disable());

        return http.build();
    }
}
