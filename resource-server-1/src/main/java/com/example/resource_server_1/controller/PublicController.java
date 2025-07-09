package com.example.resource_server_1.controller;

import com.example.resource_server_1.model.Message;
import lombok.AllArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/public")
@AllArgsConstructor
public class PublicController {
    private RestTemplate restTemplate;
    private OAuth2AuthorizedClientManager clientManager;

    @GetMapping("/get")
    public Message get() {
        return new Message("Hello from resource 1");
    }

    @GetMapping("/server2")
    public Message callServer2() {
        RequestEntity<Void> requestEntity = RequestEntity.get("http://localhost:8090/public/get")
                .build();
        ResponseEntity<Message> responseEntity = restTemplate.exchange(requestEntity, Message.class);
        return responseEntity.getBody();
    }

    @GetMapping("/private/server2")
    public Message server2() {
        OAuth2AuthorizeRequest request = OAuth2AuthorizeRequest
                .withClientRegistrationId("keycloak-client") // name on yaml
                .principal("spring-client") //can give any
                .build();
        OAuth2AuthorizedClient client = clientManager.authorize(request);

        String token = client.getAccessToken().getTokenValue();

        RequestEntity<Void> requestEntity = RequestEntity.get("http://localhost:8090/private/get")
                .headers(h->h.setBearerAuth(token))
                .build();
        ResponseEntity<Message> responseEntity = restTemplate.exchange(requestEntity, Message.class);
        return responseEntity.getBody();
    }
}
