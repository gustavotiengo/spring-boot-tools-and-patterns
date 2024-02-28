package com.gt.springtools.service;

import com.gt.springtools.cache.CacheNames;
import com.gt.springtools.dto.User;
import com.gt.springtools.exception.EntityNotFoundException;
import com.gt.springtools.exception.EntityPersistenceException;
import com.gt.springtools.repository.UserRepository;
import com.gt.springtools.tables.records.UserRecord;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public User findByUuid(String uuid) throws EntityNotFoundException {
        Optional<User> user = userRepository.findByUuid(uuid);
        return user.orElseThrow(() ->
                new EntityNotFoundException(MessageFormat.format("User {0} does not exist", uuid)));
    }

    @CacheEvict(value = CacheNames.USERS, allEntries = true)
    @Transactional
    public User save(User user, String uuid) throws EntityPersistenceException {
        UserRecord userRecord = new UserRecord();
        userRecord.from(user);

        if (Optional.ofNullable(uuid).isPresent()) {
            userRecord.setExternalId(UUID.fromString(uuid));
            if (!userRepository.update(userRecord)) {
                throw new EntityPersistenceException(
                        MessageFormat.format("User {0} cannot be updated or does not exist", uuid));
            }
        } else {
            UUID newUserUUID = userRepository.insert(userRecord);
            if (Optional.ofNullable(newUserUUID).isPresent()) {
                userRecord.setExternalId(newUserUUID);
            } else {
                throw new EntityPersistenceException("Error creating user");
            }
        }

        return new User(userRecord);
    }

    @CacheEvict(value = CacheNames.USERS, allEntries = true)
    @Transactional
    public void delete(String uuid) throws EntityNotFoundException {
        if (!userRepository.delete(uuid)) {
            throw new EntityNotFoundException(MessageFormat.format("User {0} does not exist", uuid));
        }
    }

}
