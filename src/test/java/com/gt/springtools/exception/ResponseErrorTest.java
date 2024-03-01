package com.gt.springtools.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ResponseErrorTest {

    private static final ResponseError responseError = new ResponseError(HttpStatus.OK, "message");

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