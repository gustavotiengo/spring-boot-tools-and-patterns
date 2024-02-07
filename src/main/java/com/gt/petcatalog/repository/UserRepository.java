package com.gt.petcatalog.repository;

import com.gt.petcatalog.tables.pojos.Users;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.gt.petcatalog.Tables.USERS;

@Repository
public class UserRepository {

    private final DSLContext dbContext;

    public UserRepository(final DSLContext dbContext) {
        this.dbContext = dbContext;
    }

     public List<Users> findAll() {
         return dbContext.select().from(USERS).fetchInto(Users.class);
    }
}
