package com.gt.springtools.controller;

import com.gt.springtools.Constants;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

interface BaseControllerAPI<T> {

    @GetMapping
    ResponseEntity<List<T>> findAll();

    @GetMapping("/{uuid}")
    ResponseEntity<T> findByUuid(@PathVariable @Pattern(regexp = Constants.UUID_V4) String uuid);

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable @Pattern(regexp = Constants.UUID_V4) String uuid);

}
