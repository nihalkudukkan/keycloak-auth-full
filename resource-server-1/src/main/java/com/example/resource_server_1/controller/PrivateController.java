package com.example.resource_server_1.controller;

import com.example.resource_server_1.model.Message;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/private")
@AllArgsConstructor
public class PrivateController {
    private RestTemplate restTemplate;
    private OAuth2AuthorizedClientManager clientManager;
    @GetMapping("/get")
    public Message get() {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new Message("Hello from resource 1 with token");
    }

    @GetMapping("/server2")
    public Message server2(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        RequestEntity<Void> requestEntity = RequestEntity.get("http://localhost:8090/private/get")
                .header("Authorization", token)
                .build();
        ResponseEntity<Message> responseEntity = restTemplate.exchange(requestEntity, Message.class);
        return responseEntity.getBody();
    }
}
