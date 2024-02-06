package com.gt.petcatalog.repository;

import com.gt.petcatalog.tables.pojos.Users;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.gt.petcatalog.Tables.USERS;

@Repository
public class UserRepository {

    final DSLContext create;

    public UserRepository(DSLContext create) {
        this.create = create;
    }

     public List<Users> findAll() {
         return create.select().from(USERS).fetchInto(Users.class);
    }
}
