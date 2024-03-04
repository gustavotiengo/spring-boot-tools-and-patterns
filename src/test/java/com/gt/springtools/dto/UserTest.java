package com.gt.springtools.dto;

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

    private static final User userB = new User("8108e27f-301c-4c2a-8592-de2dd34402e8",
            "John",
            "5551234",
            "john@email.com",
            "B Street",
            "Bronx",
            "NY",
            "NY",
            null,
            null);

    @Test
    void testToString() {
        String userToString = "User (8108e27f-301c-4c2a-8592-de2dd34402e8, Mary, 5551234, mary@email.com, A Street, " +
                "Bronx, New York City, NY, null, null)";
        assertThat(userToString).isEqualTo(userA.toString());
    }

    @Test
    void testEquals() {
        User u = SerializationUtils.clone(userA);
        assertThat(userA).isEqualTo(u).isNotEqualTo(userB);
        assertThat(userA.getExternalId()).isEqualTo("8108e27f-301c-4c2a-8592-de2dd34402e8");
        assertThat(userA.getName()).isEqualTo("Mary");
        assertThat(userA.getPhone()).isEqualTo("5551234");
        assertThat(userA.getEmail()).isEqualTo("mary@email.com");
        assertThat(userA.getAddress()).isEqualTo("A Street");
        assertThat(userA.getRegion()).isEqualTo("Bronx");
        assertThat(userA.getCity()).isEqualTo("New York City");
        assertThat(userA.getState()).isEqualTo("NY");
        assertThat(userA.getCreatedAt()).isNull();
        assertThat(userA.getLastUpdate()).isNull();
    }

    @Test
    void testEqualsNullObject() {
        assertThat(userA).isNotEqualTo(null).isNotEqualTo(userB);
        assertThat((User) null).isNotEqualTo(userA).isNotEqualTo(userB);
    }

    @Test
    void testHashCode() {
        User u = SerializationUtils.clone(userA);
        assertThat(userA.hashCode()).isEqualTo(u.hashCode()).isNotEqualTo(userB.hashCode());
    }


}