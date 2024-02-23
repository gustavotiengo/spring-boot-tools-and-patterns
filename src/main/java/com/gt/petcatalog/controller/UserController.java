package com.gt.petcatalog.controller;

import com.gt.petcatalog.Consts;
import com.gt.petcatalog.service.UserService;
import com.gt.petcatalog.tables.pojos.Users;
import jakarta.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;



@RestController
@RequestMapping("users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Users> findByUuid(@PathVariable @Pattern(regexp = Consts.UUID_V4) String uuid) {
        logger.debug("Find user {}", uuid);
        Users user = userService.findByUuid(uuid);
        return user != null ? ResponseEntity.ok(userService.findByUuid(uuid)) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Users>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping
    public ResponseEntity<Users> save(@RequestBody final Users user) {
        final Optional<Users> saved = this.userService.save(user);

        // if saved is empty, return accepted because fallback process will be executed
        if (saved.isEmpty()) {
            logger.error("TO-DO FALLBACK");
            return ResponseEntity.accepted().build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(saved.get());
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<String> delete(@PathVariable @Pattern(regexp = Consts.UUID_V4) String uuid) {
        if (userService.delete(uuid)) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

}
