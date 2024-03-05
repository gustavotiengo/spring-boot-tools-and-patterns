package com.gt.springtools.repository;

import com.gt.springtools.dto.User;
import com.gt.springtools.tables.records.UserRecord;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.gt.springtools.Tables.USER;

@Repository
public class UserRepository {

    private static final int QUERY_HARD_LIMIT = 1000;

    private final DSLContext dbContext;

    public UserRepository(final DSLContext dbContext) {
        this.dbContext = dbContext;
    }

    public List<User> findAll() {
        return dbContext.select().from(USER).limit(QUERY_HARD_LIMIT).fetchInto(User.class);
    }

    public Optional<User> findByUuid(String uuid) {
        return Optional.ofNullable(dbContext.select()
                .from(USER)
                .where(USER.EXTERNAL_ID.eq(UUID.fromString(String.valueOf(uuid))))
                .limit(1)
                .fetchOneInto(User.class));
    }

    public UUID insert(UserRecord user) {
        return dbContext.insertInto(USER)
                .set(user)
                .set(USER.CREATED_AT, LocalDateTime.now())
                .set(USER.LAST_UPDATE, LocalDateTime.now())
                .returningResult(USER.EXTERNAL_ID)
                .fetchOneInto(UUID.class);
    }

    public Boolean update(UserRecord user) {
        user.reset(USER.CREATED_AT);

        int execution = dbContext.update(USER)
                .set(user)
                .set(USER.LAST_UPDATE, LocalDateTime.now())
                .where(USER.EXTERNAL_ID.eq(user.getExternalId()))
                .execute();

        return execution == 1;
    }

    public Boolean delete(String uuid) {
        int execution = dbContext.
                deleteFrom(USER).
                where(USER.EXTERNAL_ID.eq(UUID.fromString(uuid))).
                execute();

        return execution == 1;
    }
}
