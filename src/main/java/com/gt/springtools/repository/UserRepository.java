package com.gt.springtools.repository;

import com.gt.springtools.dto.User;
import com.gt.springtools.tables.records.UsersRecord;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static com.gt.springtools.Tables.USERS;

@Repository
public class UserRepository {

    private final DSLContext dbContext;

    public UserRepository(final DSLContext dbContext) {
        this.dbContext = dbContext;
    }

    public List<User> findAll() {
        return dbContext.
                select().
                from(USERS).
                fetchInto(User.class);
    }


    public User findByUuid(String uuid) {
        return dbContext.
                select().
                from(USERS).
                where(USERS.EXTERNAL_ID.eq(UUID.fromString(uuid))).
                limit(1).
                fetchOneInto(User.class);
    }

    public boolean persist(UsersRecord user) {
        int execution = dbContext.
                insertInto(USERS).
                set(user).
                onDuplicateKeyUpdate().
                set(user).execute();

        return execution == 1;
    }

    public boolean delete(String uuid) {
        int execution = dbContext.
                deleteFrom(USERS).
                where(USERS.EXTERNAL_ID.eq(UUID.fromString(uuid))).
                execute();

        return execution == 1;
    }
}
