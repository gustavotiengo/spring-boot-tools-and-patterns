package com.gt.petcatalog.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PetController {

    @GetMapping("/pets")
    private ResponseEntity<String> findById() {
        return ResponseEntity.ok("pets");
    }
}
