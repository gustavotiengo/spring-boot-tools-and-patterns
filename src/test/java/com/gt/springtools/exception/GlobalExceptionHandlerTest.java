package com.gt.springtools.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gt.springtools.controller.UserController;
import com.gt.springtools.dto.UserDTO;
import com.gt.springtools.service.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class GlobalExceptionHandlerTest {

    private static final UserDTO validUser = new UserDTO(null,
            "Mary",
            "+55 11 4441234",
            "mary@email.com",
            "Mary Street",
            "Bronx",
            "New York",
            "NY",
            null,
            null);

    private static final UserDTO invalidUser = new UserDTO(null,
            "",
            "Random",
            "WRONG EMAIL PATTERN",
            "Random Street",
            "Random",
            "Random",
            "WRONG STATE PATTERN",
            null,
            null);

    static final ObjectMapper mapper = new ObjectMapper();
    static {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @MockBean
    UserService userService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void testHandleMethodArgumentNotValidException() throws Exception {
        mockMvc.perform(post("/users").content(mapper.writeValueAsString(invalidUser))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors", Matchers.hasSize(3)));;
    }

    @Test
    void testHandleMethodValidationException() throws Exception {
        mockMvc.perform(put("/users/0ea9c10b-8d45-4c42-bbe0-8f30ea6f35df").content(mapper.writeValueAsString(invalidUser))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors", Matchers.hasSize(3)));
    }

    @Test
    void testHandleMethodValidationExceptionInvalidPathVariable() throws Exception {
        mockMvc.perform(put("/users/*(!)").content(mapper.writeValueAsString(invalidUser))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors", Matchers.hasSize(Matchers.greaterThanOrEqualTo(4))));
    }

    @Test
    void testHandleHttpRequestMethodNotSupportedException() throws Exception {
        mockMvc.perform(patch("/users").content(mapper.writeValueAsString(validUser))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isMethodNotAllowed());
    }

    @Test
    void  testHandleNoResourceFoundException() throws Exception {
        mockMvc.perform(get("/favicon.ico")).andExpect(status().isBadRequest());
    }

    @Test
    void testHandleException() throws Exception {
        Mockito.doThrow(RuntimeException.class).when(userService).findAll();
        mockMvc.perform(get("/users")).andExpect(status().isInternalServerError());
    }


}