package com.gt.springtools.service;

import com.gt.springtools.cache.CacheNames;
import com.gt.springtools.dto.UserDTO;
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
public class UserService implements BaseService<UserDTO> {

    private final UserRepository userRepository;

    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Cacheable(CacheNames.USERS)
    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public UserDTO findByUuid(String uuid) throws EntityNotFoundException {
        Optional<UserDTO> user = userRepository.findByUuid(uuid);
        return user.orElseThrow(() ->
                new EntityNotFoundException(MessageFormat.format("User {0} does not exist", uuid)));
    }

    @CacheEvict(value = CacheNames.USERS, allEntries = true)
    @Transactional
    public UserDTO save(UserDTO user, String uuid) throws EntityPersistenceException {
        UserRecord userRecord = new UserRecord();
        userRecord.from(user);

        if (Optional.ofNullable(uuid).isPresent()) {
            userRecord.setExternalId(UUID.fromString(uuid));
            if (Boolean.FALSE.equals(userRepository.update(userRecord))) {
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

        return new UserDTO(userRecord);
    }

    @CacheEvict(value = CacheNames.USERS, allEntries = true)
    @Transactional
    public void delete(String uuid) throws EntityNotFoundException {
        if (Boolean.FALSE.equals(userRepository.delete(uuid))) {
            throw new EntityNotFoundException(MessageFormat.format("User {0} does not exist", uuid));
        }
    }

}
