package com.gt.springtools.service;

import com.gt.springtools.dto.UserDTO;
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

    private static final UserDTO existentUser = new UserDTO("0ea9c10b-8d45-4c42-bbe0-8f30ea6f35df",
            "John",
            "+55 11 5551122",
            "john@email.com",
            "Test Street",
            "Manhattan",
            "New York",
            "NY",
            LocalDateTime.MAX,
            LocalDateTime.MIN);

    private static final UserDTO newUser = new UserDTO(null,
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
        List<UserDTO> usersEmptyList = new ArrayList<>();

        Mockito.when(userRepository.findAll()).thenReturn(usersEmptyList);
        List<UserDTO> usersFromService = userService.findAll();

        assertThat(usersFromService).isNotNull().isEmpty();
    }

    @Test
    void findAll_WithResults() {
        List<UserDTO> usersList = new ArrayList<>();
        usersList.add(existentUser);

        Mockito.when(userRepository.findAll()).thenReturn(usersList);
        List<UserDTO> usersFromService = userService.findAll();

        assertThat(usersFromService).isNotNull().hasSize(1);
    }

    @Test
    void findByUuid_ExistentUser() {
        Mockito.when(userRepository.findByUuid(any())).thenReturn(Optional.of(existentUser));
        UserDTO user = userService.findByUuid(any());

        assertThat(user).isNotNull();
        assertThat(user.getExternalId()).isEqualTo("0ea9c10b-8d45-4c42-bbe0-8f30ea6f35df");
        assertThat(user.getName()).isEqualTo("John");
        assertThat(user.getPhone()).isEqualTo("+55 11 5551122");
        assertThat(user.getEmail()).isEqualTo("john@email.com");
        assertThat(user.getAddress()).isEqualTo("Test Street");
        assertThat(user.getRegion()).isEqualTo("Manhattan");
        assertThat(user.getCity()).isEqualTo("New York");
        assertThat(user.getState()).isEqualTo("NY");
        assertThat(user.getCreatedAt()).isEqualTo(LocalDateTime.MAX);
        assertThat(user.getLastUpdate()).isEqualTo(LocalDateTime.MIN);

    }

    @Test
    void findByUuid_NonExistentUser() {
        Mockito.when(userRepository.findByUuid(any())).thenReturn(Optional.empty());
        Exception e = assertThrows(Exception.class, () -> {
            userService.findByUuid("0ea9c10b-8d45-4c42-bbe0-8f30ea6f35df");
        });

        assertThat(e).isOfAnyClassIn(EntityNotFoundException.class);
        assertThat(e.getMessage()).isEqualTo("User 0ea9c10b-8d45-4c42-bbe0-8f30ea6f35df does not exist");
    }

    @Test
    void save_UpdateExistentUser_Success() {
        Mockito.when(userRepository.update(any())).thenReturn(Boolean.TRUE);
        UserDTO user = userService.save(existentUser, existentUser.getExternalId());
        assertThat(user).isNotNull();
        assertThat(user.getExternalId()).isEqualTo("0ea9c10b-8d45-4c42-bbe0-8f30ea6f35df");
        assertThat(user.getName()).isEqualTo("John");
        assertThat(user.getPhone()).isEqualTo("+55 11 5551122");
        assertThat(user.getEmail()).isEqualTo("john@email.com");
        assertThat(user.getAddress()).isEqualTo("Test Street");
        assertThat(user.getRegion()).isEqualTo("Manhattan");
        assertThat(user.getCity()).isEqualTo("New York");
        assertThat(user.getState()).isEqualTo("NY");
        assertThat(user.getCreatedAt()).isEqualTo(LocalDateTime.MAX);
        assertThat(user.getLastUpdate()).isEqualTo(LocalDateTime.MIN);
    }

    @Test
    void save_UpdateExistentUser_Failure() {
        Mockito.when(userRepository.update(any())).thenReturn(Boolean.FALSE);
        Exception e = assertThrows(Exception.class, () -> {
            userService.save(newUser, "0ea9c10b-8d45-4c42-bbe0-8f30ea6f35df");
        });

        assertThat(e).isOfAnyClassIn(EntityPersistenceException.class);
        assertThat(e.getMessage()).isEqualTo(
                "User 0ea9c10b-8d45-4c42-bbe0-8f30ea6f35df cannot be updated or does not exist");
    }

    @Test
    void save_CreateNewUser_Success() {
        UUID uuid = UUID.randomUUID();
        Mockito.when(userRepository.insert(any())).thenReturn(uuid);
        UserDTO user = userService.save(newUser, null);
        assertThat(user).isNotNull();
        assertThat(user.getExternalId()).isEqualTo(uuid.toString());
        assertThat(user.getName()).isEqualTo("Mary");
        assertThat(user.getPhone()).isEqualTo("+55 11 4441234");
        assertThat(user.getEmail()).isEqualTo("mary@email.com");
        assertThat(user.getAddress()).isEqualTo("Mary Street");
        assertThat(user.getRegion()).isEqualTo("Bronx");
        assertThat(user.getCity()).isEqualTo("New York");
        assertThat(user.getState()).isEqualTo("NY");
        assertThat(user.getCreatedAt()).isNull();
        assertThat(user.getLastUpdate()).isNull();
    }

    @Test
    void save_CreateNewUser_Failure() {
        Mockito.when(userRepository.insert(any())).thenReturn(null);
        Exception e = assertThrows(Exception.class, () -> {
            userService.save(newUser, null);
        });

        assertThat(e).isOfAnyClassIn(EntityPersistenceException.class);
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
        Exception e = assertThrows(Exception.class, () -> {
            userService.delete("0ea9c10b-8d45-4c42-bbe0-8f30ea6f35df");
        });

        assertThat(e).isOfAnyClassIn(EntityNotFoundException.class);
        assertThat(e.getMessage()).isEqualTo("User 0ea9c10b-8d45-4c42-bbe0-8f30ea6f35df does not exist");
    }

}