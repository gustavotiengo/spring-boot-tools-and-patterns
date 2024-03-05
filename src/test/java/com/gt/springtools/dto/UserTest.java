package com.gt.springtools.dto;

import com.gt.springtools.tables.records.UserRecord;
import org.junit.jupiter.api.Test;
import org.springframework.util.SerializationUtils;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    private static final User userA = new User("8108e27f-301c-4c2a-8592-de2dd34402e8",
            "Mary",
            "5551234",
            "mary@email.com",
            "A Street",
            "Bronx",
            "New York City",
            "NY",
            null,
            null);

    private static final User userB = new User("4b4460d6-cdd2-4627-9ab2-e2d7b3bf1f10",
            "John",
            "5553456",
            "john@email.com",
            "B Street",
            "Bronx",
            "NY",
            "NY",
            null,
            null);

    @Test
    void testToString() {
        String userToString = "User (8108e27f-301c-4c2a-8592-de2dd34402e8, Mary, 5551234, mary@email.com, A Street, Bronx, New York City, NY, null, null)";
        assertThat(userToString).isEqualTo(userA.toString());
    }

    @Test
    void testEquals() {
        User u = SerializationUtils.clone(userA);
        assertThat(userA).isEqualTo(u).isNotEqualTo(userB);
    }

    @Test
    void testEqualsNullObject() {
        assertThat(userA).isNotEqualTo(null).isNotEqualTo(userB);
    }

    @Test
    void testHashCode() {
        User u = SerializationUtils.clone(userA);
        assertThat(userA.hashCode()).isEqualTo(u.hashCode()).isNotEqualTo(userB.hashCode());
    }

    @Test
    void testHashCodeNullFields() {
        UserRecord userRecord = new UserRecord();
        User user1 = new User(userRecord);
        User user2 = new User(userRecord);
        assertThat(userA.hashCode()).isNotEqualTo(user1.hashCode());
        assertThat(user1.hashCode()).hasSameHashCodeAs(user2.hashCode());
    }


}