package com.gt.petcatalog.controller;

import com.gt.petcatalog.tables.Users;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PetController {

    @Autowired
    private DSLContext create;

    @GetMapping("/pets")
    private ResponseEntity<String> findById() {
        Result<Record> result = create.select().from(Users.USERS).fetch();
        return ResponseEntity.ok("pets");
    }
}