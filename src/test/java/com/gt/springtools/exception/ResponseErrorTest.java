package com.gt.springtools.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseErrorTest {

    private static final ResponseError responseError;

    static {
        responseError = new ResponseError(HttpStatus.OK, "message");
    }

    @Test
    void testResponseErrorConstructor() {
        assertThat(responseError.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(responseError.getMessage()).isEqualTo("message");
        assertThat(responseError.getErrors()).isNullOrEmpty();
    }

    @Test
    void testSetStatus() {
        responseError.setStatus(HttpStatus.NOT_FOUND);
        assertThat(responseError.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testSetMessage() {
        responseError.setMessage("error");
        assertThat(responseError.getMessage()).isEqualTo("error");
    }

}