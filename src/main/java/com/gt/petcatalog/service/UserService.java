package com.gt.petcatalog.service;

import com.gt.petcatalog.cache.CacheNames;
import com.gt.petcatalog.dto.User;
import com.gt.petcatalog.exception.EntityNotFoundException;
import com.gt.petcatalog.repository.UserRepository;
import com.gt.petcatalog.tables.records.UsersRecord;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.gt.petcatalog.Tables.USERS;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Cacheable(CacheNames.USERS)
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User findByUuid(String uuid) {
        User user = userRepository.findByUuid(uuid);
        if (user == null) {
            throw new EntityNotFoundException(MessageFormat.format("User {0} does not exist", uuid));
        }

        return user;
    }

    @CacheEvict(value = CacheNames.USERS, allEntries = true)
    @Transactional
    public Optional<User> save(User user) throws RuntimeException {
        UsersRecord userRecord = new UsersRecord();
        userRecord.from(user);

        if (user.getExternalId() == null) {
            userRecord.setExternalId(UUID.randomUUID());
            userRecord.setCreatedAt(LocalDateTime.now());
        } else {
            userRecord.reset(USERS.CREATED_AT);
            userRecord.setLastUpdate(LocalDateTime.now());
        }

        if (!userRepository.persist(userRecord)) {
            throw new RuntimeException("Error persisting user");
        }

        return Optional.of(user);
    }

    @CacheEvict(value = CacheNames.USERS, allEntries = true)
    @Transactional
    public void delete(String uuid) throws EntityNotFoundException {
        if (!userRepository.delete(uuid)) {
            throw new EntityNotFoundException(MessageFormat.format("User {0} does not exist", uuid));
        }

    }

}
