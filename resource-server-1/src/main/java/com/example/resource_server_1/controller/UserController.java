package com.example.resource_server_1.controller;

import com.example.resource_server_1.model.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @GetMapping("/get")
    public Message get() {
        return new Message("User logged");
    }
}
