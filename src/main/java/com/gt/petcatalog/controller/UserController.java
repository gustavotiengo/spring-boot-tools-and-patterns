package com.gt.petcatalog.controller;

import com.gt.petcatalog.service.UserService;
import com.gt.petcatalog.tables.pojos.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> findById(@PathVariable Integer id) {
        return ResponseEntity.ok("user");
    }

    @GetMapping
    public ResponseEntity<List<Users>> findAll() {
        logger.debug("======================== LOG =========================");
        return ResponseEntity.ok(userService.findAll());
    }
}
