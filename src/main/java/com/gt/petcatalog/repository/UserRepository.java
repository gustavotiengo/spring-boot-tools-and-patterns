package com.gt.petcatalog.repository;

import com.gt.petcatalog.tables.pojos.Users;
import com.gt.petcatalog.tables.records.UsersRecord;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static com.gt.petcatalog.Tables.USERS;

@Repository
public class UserRepository {

    private final DSLContext dbContext;

    public UserRepository(final DSLContext dbContext) {
        this.dbContext = dbContext;
    }

     public List<Users> findAll() {
         return dbContext.
                 select().
                 from(USERS).
                 fetchInto(Users.class);
    }

    public Users findByUuid(String uuid) {
        return dbContext.
                select().
                from(USERS).
                where(USERS.EXTERNAL_ID.eq(UUID.fromString(uuid))).
                limit(1).
                fetchOneInto(Users.class);
    }

    public Users persist(Users user) {
        UsersRecord record = new UsersRecord(user);

        if (user.getExternalId() == null) {
            record.setExternalId(UUID.randomUUID());
        }

        int execution = dbContext.
                insertInto(USERS).
                set(record).
                onDuplicateKeyUpdate().
                set(record).execute();

        return null;
    }
}
