package com.gt.springtools.controller;

import com.gt.springtools.Constants;
import com.gt.springtools.dto.UserDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

interface UserControllerAPI extends BaseControllerAPI<UserDTO> {

    @PostMapping
    ResponseEntity<UserDTO> create(@RequestBody @Valid final UserDTO user);

    @PutMapping("/{uuid}")
    public ResponseEntity<UserDTO> update(@PathVariable @Pattern(regexp = Constants.UUID_V4) String uuid,
                                          @RequestBody @Valid final UserDTO user);

}
