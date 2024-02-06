package com.gt.petcatalog.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/user")
    private ResponseEntity<String> findById() {
        return ResponseEntity.ok("user");
    }
}
