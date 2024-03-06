package com.gt.springtools.controller;

import com.gt.springtools.Constants;
import com.gt.springtools.dto.UserDTO;
import com.gt.springtools.service.BaseService;
import com.gt.springtools.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.text.MessageFormat;

import static com.gt.springtools.controller.UserController.USERS;

@RestController
@RequestMapping(USERS)
public class UserController extends BaseController<UserDTO> implements UserControllerAPI {

    static final String USERS = "users";

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @Override
    protected BaseService<UserDTO> service() {
        return this.userService;
    }

    @Override
    public ResponseEntity<UserDTO> create(@RequestBody @Valid final UserDTO user) {
        final UserDTO createdUser = userService.save(user, null);
        return ResponseEntity
                .created(URI.create(MessageFormat.format("/{0}/{1}", USERS, createdUser.getExternalId())))
                .body(createdUser);
    }

    @Override
    public ResponseEntity<UserDTO> update(@PathVariable @Pattern(regexp = Constants.UUID_V4) String uuid,
                                          @RequestBody @Valid final UserDTO user) {
        final UserDTO updatedUser = userService.save(user, uuid);
        return ResponseEntity.ok(updatedUser);
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable @Pattern(regexp = Constants.UUID_V4) String uuid) {
        userService.delete(uuid);
        return ResponseEntity.noContent().build();
    }

}

