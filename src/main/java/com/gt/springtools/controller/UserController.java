package com.gt.springtools.controller;

import com.gt.springtools.Constants;
import com.gt.springtools.dto.UserDTO;
import com.gt.springtools.service.BaseService;
import com.gt.springtools.service.UserService;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(Constants.USERS)
public class UserController extends BaseController<UserDTO> implements UserControllerAPI {

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @Override
    protected BaseService<UserDTO> service() {
        return this.userService;
    }

    @Override
    protected String path() {
        return Constants.USERS;
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable @Pattern(regexp = Constants.UUID_V4) String uuid) {
        userService.delete(uuid);
        logger.debug("Delete with id {}", uuid);
        return ResponseEntity.noContent().build();
    }

}

