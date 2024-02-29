package com.gt.springtools.controller;

import com.gt.springtools.Constants;
import com.gt.springtools.dto.User;
import com.gt.springtools.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.text.MessageFormat;
import java.util.List;

import static com.gt.springtools.controller.UserController.USERS;


@RestController
@RequestMapping(USERS)
public class UserController {

    static final String USERS = "users";

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody @Valid final User user) {
        final User createdUser = userService.save(user, null);
        return ResponseEntity
                .created(URI.create(MessageFormat.format("/{0}/{1}", USERS, createdUser.getExternalId())))
                .body(createdUser);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<User> findByUuid(@PathVariable @Pattern(regexp = Constants.UUID_V4) String uuid) {
        logger.debug("Find user {}", uuid);
        logger.info("Find user {}", uuid);
        final User user = userService.findByUuid(uuid);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<User> update(@PathVariable @Pattern(regexp = Constants.UUID_V4) String uuid,
                                       @RequestBody @Valid final User user) {
        final User updatedUser = userService.save(user, uuid);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Object> delete(@PathVariable @Pattern(regexp = Constants.UUID_V4) String uuid) {
        userService.delete(uuid);
        return ResponseEntity.noContent().build();
    }

}
