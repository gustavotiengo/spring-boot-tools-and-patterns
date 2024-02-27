package com.gt.springtools.exception;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.MessageFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GlobalExceptionHandlerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testHandleResourceNotFoundException() {
        ResponseEntity<ResponseError> response = restTemplate.
                getForEntity("/users/e3b5d6c8-78a9-433d-af34-06e0c25902a0", ResponseError.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(MessageFormat.format("User {0} does not exist", "e3b5d6c8-78a9-433d-af34-06e0c25902a0"), response.getBody().getMessage());
    }

}
