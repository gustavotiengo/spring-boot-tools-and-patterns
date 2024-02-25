package com.gt.petcatalog.service;

import com.gt.petcatalog.cache.CacheNames;
import com.gt.petcatalog.repository.UserRepository;
import com.gt.petcatalog.tables.pojos.Users;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
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
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Transactional(readOnly = true)
    public Users findByUuid(String uuid) {
        return userRepository.findByUuid(uuid);
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
    public void delete(String uuid) throws RuntimeException {
        if (!userRepository.delete(uuid)) {
            throw new RuntimeException("NOT DELETED");
        }

    }

}
