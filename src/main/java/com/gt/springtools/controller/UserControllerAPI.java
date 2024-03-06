package com.gt.springtools.controller;

import com.gt.springtools.Constants;
import com.gt.springtools.dto.UserDTO;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

interface UserControllerAPI extends BaseControllerAPI<UserDTO> {

    @Override
    @DeleteMapping("/{uuid}")
    ResponseEntity<Void> delete(@PathVariable @Pattern(regexp = Constants.UUID_V4) String uuid);

}
