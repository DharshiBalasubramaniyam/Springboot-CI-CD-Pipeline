package com.dharshi.springboot_cicd.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/home")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Welcome to Spring Boot CI/CD Pipeline!");
    }

}

