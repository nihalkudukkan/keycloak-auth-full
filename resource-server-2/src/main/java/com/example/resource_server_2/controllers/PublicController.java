package com.example.resource_server_2.controllers;

import com.example.resource_server_2.model.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {
    @GetMapping("/get")
    public Message get() {
        return new Message("Hello from resource 2");
    }
}
