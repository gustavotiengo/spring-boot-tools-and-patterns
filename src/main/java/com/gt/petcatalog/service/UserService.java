package com.gt.petcatalog.service;

import com.gt.petcatalog.cache.CacheNames;
import com.gt.petcatalog.exception.EntityNotFoundException;
import com.gt.petcatalog.repository.UserRepository;
import com.gt.petcatalog.tables.pojos.Users;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Cacheable(CacheNames.USERS)
    @Transactional(readOnly = true)
    public List<Users> findAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Users findByUuid(String uuid) {
        Users user = userRepository.findByUuid(uuid);
        if (user == null) {
            throw new EntityNotFoundException(MessageFormat.format("User {0} does not exist", uuid));
        }

        return user;
    }

    @CacheEvict(value = CacheNames.USERS, allEntries = true)
    @Transactional
    public Optional<Users> save(Users user) {
        try {
            final Users persisted = userRepository.persist(user);
            return Optional.of(persisted);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @CacheEvict(value = CacheNames.USERS, allEntries = true)
    @Transactional
    public void delete(String uuid) throws EntityNotFoundException {
        if (!userRepository.delete(uuid)) {
            throw new EntityNotFoundException(MessageFormat.format("User {0} does not exist", uuid));
        }

    }

}
