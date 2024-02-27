package com.gt.springtools.controller;

import com.gt.springtools.Constants;
import com.gt.springtools.dto.User;
import com.gt.springtools.service.UserService;
import jakarta.validation.Valid;
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

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<User> findByUuid(@PathVariable @Pattern(regexp = Constants.UUID_V4) String uuid) {
        logger.debug("Find user {}", uuid);
        final User user = userService.findByUuid(uuid);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping
    public ResponseEntity<User> save(@RequestBody @Valid final User user) {
        final Optional<User> saved = userService.save(user);

        if (saved.isEmpty()) {
            logger.error("TO-DO FALLBACK");
            return ResponseEntity.accepted().build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(saved.get());
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Object> delete(@PathVariable @Pattern(regexp = Constants.UUID_V4) String uuid) {
        userService.delete(uuid);
        return ResponseEntity.noContent().build();
    }

}
