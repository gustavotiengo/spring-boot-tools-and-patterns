package com.gt.springtools.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gt.springtools.dto.User;
import com.gt.springtools.exception.EntityNotFoundException;
import com.gt.springtools.exception.EntityPersistenceException;
import com.gt.springtools.service.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @MockBean
    UserService userService;

    @Autowired
    MockMvc mockMvc;

    private static final User existentUser = new User("0ea9c10b-8d45-4c42-bbe0-8f30ea6f35df",
            "John",
            "+55 11 5551122",
            "john@email.com",
            "Test Street",
            "Manhattan",
            "New York",
            "NY",
            LocalDateTime.now(),
            LocalDateTime.now());

    private static final User newUser = new User(null,
            "Mary",
            "+55 11 4441234",
            "mary@email.com",
            "Mary Street",
            "Bronx",
            "New York",
            "NY",
            null,
            null);

    static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @Test
    void findAll_NoResults() throws Exception {
        List<User> users = new ArrayList<>();
        Mockito.when(userService.findAll()).thenReturn(users);
        mockMvc.perform(get("/users")).andExpect(status().isOk()).andExpect(jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    void findAll_WithResults() throws Exception {
        List<User> users = new ArrayList<>();
        users.add(existentUser);
        Mockito.when(userService.findAll()).thenReturn(users);
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].name", Matchers.is("John")));
    }

    @Test
    void findByUuid_ExistentUser() throws Exception {
        Mockito.when(userService.findByUuid(any())).thenReturn(existentUser);
        mockMvc.perform(get("/users/0ea9c10b-8d45-4c42-bbe0-8f30ea6f35df"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", Matchers.is("John")))
                .andExpect(jsonPath("phone", Matchers.is("+55 11 5551122")))
                .andExpect(jsonPath("email", Matchers.is("john@email.com")))
                .andExpect(jsonPath("address", Matchers.is("Test Street")))
                .andExpect(jsonPath("region", Matchers.is("Manhattan")))
                .andExpect(jsonPath("city", Matchers.is("New York")))
                .andExpect(jsonPath("state", Matchers.is("NY")));
    }

    @Test
    void findByUuid_NonExistentUser() throws Exception {
        Mockito.when(userService.findByUuid(any()))
                .thenThrow(EntityNotFoundException.class);
        mockMvc.perform(get("/users/1234c10b-8d45-4c42-bbe0-8f30ea6f1234")).andExpect(status().isNotFound());
    }

    @Test
    void update_whenPutUser() throws Exception {
        Mockito.when(userService.save(newUser, "0ea9c10b-8d45-4c42-bbe0-8f30ea6f35df")).thenReturn(newUser);
        mockMvc.perform(put("/users/0ea9c10b-8d45-4c42-bbe0-8f30ea6f35df").content(mapper.writeValueAsString(newUser))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void update_whenPutNonExistentUser() throws Exception {
        Mockito.when(userService.save(newUser, "1234c10b-8d45-4c42-bbe0-8f30ea6f1234"))
                .thenThrow(EntityPersistenceException.class);
        mockMvc.perform(put("/users/1234c10b-8d45-4c42-bbe0-8f30ea6f1234")).andExpect(status().isBadRequest());
    }

    @Test
    void create_whenPostUser() throws Exception {
        Mockito.when(userService.save(newUser, null)).thenReturn(newUser);
        mockMvc.perform(post("/users").content(mapper.writeValueAsString(newUser))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("name", Matchers.is("Mary")));
    }

    @Test
    void delete_whenDeleteUser() throws Exception {
        mockMvc.perform(delete("/users/0ea9c10b-8d45-4c42-bbe0-8f30ea6f35df")).andExpect(status().isNoContent());
    }

    @Test
    void delete_whenDeleteNonExistentUser() throws Exception {
        Mockito.doThrow(EntityNotFoundException.class).when(userService).delete(any());
        mockMvc.perform(delete("/users/1234c10b-8d45-4c42-bbe0-8f30ea6f1234")).andExpect(status().isNotFound());
    }

}