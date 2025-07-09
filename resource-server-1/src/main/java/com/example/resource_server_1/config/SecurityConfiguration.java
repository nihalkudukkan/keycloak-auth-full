package com.example.resource_server_1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@EnableWebSecurity(debug = true)
@Configuration
public class SecurityConfiguration {

    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
//        converter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());
        converter.setJwtGrantedAuthoritiesConverter(source -> {
            Collection<GrantedAuthority> auth = new ArrayList<>();
            Map<String, Object> claims = source.getClaim("realm_access");
            if (null!=claims && claims.containsKey("roles")) {
                List<String> roles = (List<String>) claims.get("roles");
                roles.forEach(role->auth.add(new SimpleGrantedAuthority("ROLE_" + role)));
            }
            return auth;
        });
        return converter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(c->c.configurationSource(request -> {
                    CorsConfiguration cors = new CorsConfiguration();
                    cors.addAllowedHeader("*");
                    cors.addAllowedOrigin("*");
                    cors.addAllowedMethod("*");
                    cors.setAllowCredentials(false);
                    return cors;
                }));
        http.
                authorizeHttpRequests(auth->auth
                        .requestMatchers("/public/**").permitAll()
                        .requestMatchers("/user/**").hasAnyRole("user")
                        .anyRequest().authenticated()
                );
        http.oauth2ResourceServer(oauth->oauth
                .jwt(jwt -> jwt
                        .jwtAuthenticationConverter(jwtAuthenticationConverter())
                )
        );
        return http.build();
    }
}
