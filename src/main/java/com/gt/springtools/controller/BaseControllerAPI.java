package com.gt.springtools.controller;

import com.gt.springtools.Constants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

interface BaseControllerAPI<T> {

    @GetMapping
    ResponseEntity<List<T>> findAll();

    @GetMapping("/{uuid}")
    ResponseEntity<T> findByUuid(@PathVariable @Pattern(regexp = Constants.UUID_V4) String uuid);

    @PostMapping
    ResponseEntity<T> create(@RequestBody @Valid final T entity);

    @PutMapping("/{uuid}")
    ResponseEntity<T> update(@PathVariable @Pattern(regexp = Constants.UUID_V4) String uuid,
                             @RequestBody @Valid final T entity);

    @DeleteMapping("/{uuid}")
    default ResponseEntity<Void> delete(@PathVariable @Pattern(regexp = Constants.UUID_V4) String uuid) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
    }

}
