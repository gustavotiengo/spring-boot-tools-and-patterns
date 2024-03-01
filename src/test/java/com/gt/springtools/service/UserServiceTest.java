package com.gt.springtools.service;

import com.gt.springtools.dto.User;
import com.gt.springtools.exception.EntityNotFoundException;
import com.gt.springtools.exception.EntityPersistenceException;
import com.gt.springtools.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

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


    @Test
    void findAll_NoResults() {
        List<User> usersEmptyList = new ArrayList<>();

        Mockito.when(userRepository.findAll()).thenReturn(usersEmptyList);
        List<User> usersFromService = userService.findAll();

        assertThat(usersFromService).isNotNull().isEmpty();
    }

    @Test
    void findAll_WithResults() {
        List<User> usersList = new ArrayList<>();
        usersList.add(existentUser);

        Mockito.when(userRepository.findAll()).thenReturn(usersList);
        List<User> usersFromService = userService.findAll();

        assertThat(usersFromService).isNotNull().hasSize(1);
    }

    @Test
    void findByUuid_ExistentUser() {
        Mockito.when(userRepository.findByUuid(any())).thenReturn(Optional.of(existentUser));
        User user = userService.findByUuid(any());

        assertThat(user).isNotNull();
        assertThat(user.getName()).isEqualTo("John");
    }

    @Test
    void findByUuid_NonExistentUser() {
        Mockito.when(userRepository.findByUuid(any())).thenReturn(Optional.empty());
        EntityNotFoundException e = assertThrows(EntityNotFoundException.class, () -> {
            userService.findByUuid("0ea9c10b-8d45-4c42-bbe0-8f30ea6f35df");
        });

        assertThat(e.getMessage()).isEqualTo("User 0ea9c10b-8d45-4c42-bbe0-8f30ea6f35df does not exist");
    }

    @Test
    void save_UpdateExistentUser_Success() {
        Mockito.when(userRepository.update(any())).thenReturn(Boolean.TRUE);
        User user = userService.save(existentUser, existentUser.getExternalId());
        assertThat(user).isNotNull();
        assertThat(user.getExternalId()).isEqualTo(existentUser.getExternalId());
        assertThat(user.getName()).isEqualTo("John");
    }

    @Test
    void save_UpdateExistentUser_Failure() {
        Mockito.when(userRepository.update(any())).thenReturn(Boolean.FALSE);
        EntityPersistenceException e = assertThrows(EntityPersistenceException.class, () -> {
            userService.save(newUser, "0ea9c10b-8d45-4c42-bbe0-8f30ea6f35df");
        });

        assertThat(e.getMessage()).isEqualTo(
                "User 0ea9c10b-8d45-4c42-bbe0-8f30ea6f35df cannot be updated or does not exist");
    }

    @Test
    void save_CreateNewUser_Success() {
        Mockito.when(userRepository.insert(any())).thenReturn(UUID.randomUUID());
        User user = userService.save(newUser, null);
        assertThat(user).isNotNull();
        assertThat(user.getName()).isEqualTo("Mary");
    }

    @Test
    void save_CreateNewUser_Failure() {
        Mockito.when(userRepository.insert(any())).thenReturn(null);
        EntityPersistenceException e = assertThrows(EntityPersistenceException.class, () -> {
            userService.save(newUser, null);
        });

        assertThat(e.getMessage()).isEqualTo("Error creating user");
    }

    @Test
    void delete_ExistentUser() {
        Mockito.doReturn(Boolean.TRUE).when(userRepository).delete(any());
        assertDoesNotThrow(() -> {
            userService.delete(any());
        });
    }

    @Test
    void delete_NonExistentUser() {
        Mockito.doReturn(Boolean.FALSE).when(userRepository).delete(any());
        EntityNotFoundException e = assertThrows(EntityNotFoundException.class, () -> {
            userService.delete("0ea9c10b-8d45-4c42-bbe0-8f30ea6f35df");
        });

        assertThat(e.getMessage()).isEqualTo("User 0ea9c10b-8d45-4c42-bbe0-8f30ea6f35df does not exist");
    }

}