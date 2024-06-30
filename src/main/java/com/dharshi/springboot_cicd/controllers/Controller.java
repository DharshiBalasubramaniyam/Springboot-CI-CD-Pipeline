package com.dharshi.springboot_cicd.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class Controller {

    @GetMapping("/home")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Hello, Welcome to Spring Boot CI/CD Pipeline with GitHub Actions, Docker and AWS ECS!");
    }

}

