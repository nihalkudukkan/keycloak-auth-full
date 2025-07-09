package com.example.resource_server_1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @GetMapping("/token")
    public ResponseEntity<?> token() {
        return ResponseEntity.ok().build();
    }
}
