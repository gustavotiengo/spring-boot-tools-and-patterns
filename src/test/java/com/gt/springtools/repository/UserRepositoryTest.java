package com.gt.springtools.repository;

import com.gt.springtools.dto.User;
import com.gt.springtools.tables.records.UserRecord;
import org.jooq.DSLContext;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@JooqTest
@Testcontainers
@Sql("/test.sql")
@ActiveProfiles("tc")
class UserRepositoryTest {

    private static final PostgreSQLContainer pgContainer;
    private static final String POSTGRES_IMAGE = "postgres:15.1-alpine";
    private static final String POSTGRES_DATABASE_NAME = "springtools";

    static {
        pgContainer = new PostgreSQLContainer(POSTGRES_IMAGE);
        pgContainer.withDatabaseName(POSTGRES_DATABASE_NAME);
        pgContainer.start();
    }

    UserRepository userRepository;

    @Autowired
    DSLContext dbContext;

    @BeforeAll
    static void beforeAll() {
        pgContainer.start();
    }

    @AfterAll
    static void afterAll() {
        pgContainer.stop();
    }

    @BeforeEach
    void setUp() {
        this.userRepository = new UserRepository(dbContext);
    }

    @Test
    void shouldFindAllSuccessfully() {
        assertThat(userRepository.findAll()).isNotNull().hasSize(2);
    }

    @Test
    void shouldFindByIdSuccessfully() {
        User u = userRepository.findAll().get(0);
        Optional<User> userByUuid = userRepository.findByUuid(u.getExternalId());
        assertThat(userByUuid).isPresent();
        assertThat(userByUuid.get().getExternalId()).isNotEmpty();
    }

    @Test
    void shouldFindByIdIgnore() {
        assertThat(userRepository.findByUuid(UUID.randomUUID().toString())).isNotPresent();
    }

    @Test
    void shouldFindByIdThrowsException() {
        Exception e = assertThrows(Exception.class, () -> {
            userRepository.findByUuid("invalid-uuid");
        });

        Exception e2 = assertThrows(Exception.class, () -> {
            userRepository.findByUuid(null);
        });

        assertThat(e).isOfAnyClassIn(IllegalArgumentException.class);
        assertThat(e2).isOfAnyClassIn(IllegalArgumentException.class);
    }

    @Test
    void shouldCreateUserSuccessfully() {
        UserRecord newUser = new UserRecord(null,
                null,
                "T3",
                "3331234",
                "t3@email.com",
                "T3 Street",
                "T3",
                "T3",
                "T3",
                null,
                null);

        UUID uuidNewUser = userRepository.insert(newUser);
        assertThat(uuidNewUser).isNotNull();
        assertThat(userRepository.findAll()).isNotNull().hasSize(3);
    }

    @Test
    void shouldUpdateSuccessfully() {
        User u = userRepository.findAll().get(0);
        UserRecord userRecord = new UserRecord();
        userRecord.from(u);
        userRecord.setName("test update name");
        assertThat(userRepository.update(userRecord)).isTrue();
    }

    @Test
    void shouldUpdateIgnored() {
        User u = userRepository.findAll().get(0);
        UserRecord userRecord = new UserRecord();
        userRecord.from(u);
        userRecord.setExternalId(UUID.randomUUID());
        assertThat(userRepository.update(userRecord)).isFalse();
    }

    @Test
    void shouldUpdateThrowExceptionForNonNullField() {
        User u = userRepository.findAll().get(0);
        UserRecord userRecord = new UserRecord();
        userRecord.from(u);
        userRecord.setName(null);

        Exception e = assertThrows(Exception.class, () -> {
            userRepository.update(userRecord);
        });

        assertThat(e).isOfAnyClassIn(DataIntegrityViolationException.class);
    }

    @Test
    void shouldDeleteIgnore() {
        assertThat(userRepository.delete(UUID.randomUUID().toString())).isFalse();
        assertThat(userRepository.findAll()).isNotNull().hasSize(2);
    }

    @Test
    void shouldDeleteSuccessfully() {
        User u = userRepository.findAll().get(0);
        assertThat(userRepository.delete(u.getExternalId())).isTrue();
        assertThat(userRepository.findAll()).isNotNull().hasSize(1);
    }

}