package com.gt.springtools.controller;

import com.gt.springtools.Constants;
import com.gt.springtools.dto.UserDTO;
import com.gt.springtools.service.BaseService;
import com.gt.springtools.service.UserService;
import jakarta.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.gt.springtools.controller.UserController.USERS;

@RestController
@RequestMapping(USERS)
public class UserController extends BaseController<UserDTO> implements UserControllerAPI {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    protected static final String USERS = "users";

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @Override
    protected Logger baseLogger() {
        return logger;
    }

    @Override
    protected BaseService<UserDTO> service() {
        return this.userService;
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable @Pattern(regexp = Constants.UUID_V4) String uuid) {
        userService.delete(uuid);
        return ResponseEntity.noContent().build();
    }

}

