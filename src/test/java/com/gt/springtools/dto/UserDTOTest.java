package com.gt.springtools.dto;

import com.gt.springtools.tables.records.UserRecord;
import org.junit.jupiter.api.Test;
import org.springframework.util.SerializationUtils;

import static org.assertj.core.api.Assertions.assertThat;

class UserDTOTest {

    private static final UserDTO userA = new UserDTO("8108e27f-301c-4c2a-8592-de2dd34402e8",
            "Mary",
            "5551234",
            "mary@email.com",
            "A Street",
            "Bronx",
            "New York City",
            "NY",
            null,
            null);

    private static final UserDTO userB = new UserDTO("4b4460d6-cdd2-4627-9ab2-e2d7b3bf1f10",
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
        String userToString = "UserDTO (8108e27f-301c-4c2a-8592-de2dd34402e8, Mary, 5551234, mary@email.com, A Street, Bronx, New York City, NY, null, null)";
        assertThat(userToString).isEqualTo(userA.toString());
    }

    @Test
    void testEquals() {
        UserDTO u = SerializationUtils.clone(userA);
        assertThat(userA).isEqualTo(u).isNotEqualTo(userB);
    }

    @Test
    void testEqualsNullObject() {
        assertThat(userA).isNotEqualTo(null).isNotEqualTo(userB);
    }

    @Test
    void testHashCode() {
        UserDTO u = SerializationUtils.clone(userA);
        assertThat(userA.hashCode()).isEqualTo(u.hashCode()).isNotEqualTo(userB.hashCode());
    }

    @Test
    void testHashCodeNullFields() {
        UserRecord userRecord = new UserRecord();
        UserDTO user1 = new UserDTO(userRecord);
        UserDTO user2 = new UserDTO(userRecord);
        assertThat(userA.hashCode()).isNotEqualTo(user1.hashCode());
        assertThat(user1.hashCode()).hasSameHashCodeAs(user2.hashCode());
    }


}