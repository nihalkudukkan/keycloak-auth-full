package com.example.resource_server_1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class OtherConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
