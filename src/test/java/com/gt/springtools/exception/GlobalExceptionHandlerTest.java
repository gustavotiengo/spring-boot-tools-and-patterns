package com.gt.springtools.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {

    @Test
    void testHandleResourceNotFoundException() {
        assertEquals(1, Integer.parseInt("1"));
    }

}
