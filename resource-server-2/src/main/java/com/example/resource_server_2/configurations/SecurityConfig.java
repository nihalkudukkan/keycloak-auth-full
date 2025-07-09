package com.example.resource_server_2.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

@EnableWebSecurity(debug = true)
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.
                authorizeHttpRequests(auth->auth
                        .requestMatchers("/public/**").permitAll()
                        .anyRequest().authenticated()
                );
        http.oauth2ResourceServer(oauth->oauth
                .jwt()
        );
        http.cors(c->c.configurationSource(request -> {
            CorsConfiguration cors = new CorsConfiguration();
            cors.addAllowedHeader("*");
            cors.addAllowedOrigin("*");
            cors.addAllowedMethod("*");
            cors.setAllowCredentials(false);
            return cors;
        }));
        return http.build();
    }
}
