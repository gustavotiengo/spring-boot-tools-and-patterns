package com.gt.petcatalog.service;

import com.gt.petcatalog.cache.CacheNames;
import com.gt.petcatalog.repository.UserRepository;
import com.gt.petcatalog.tables.pojos.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    @Autowired
    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Cacheable(CacheNames.USERS)
    public List<Users> findAll() {
        logger.debug("============ Fetch all users from Postres ============");
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public Users findByUuid(String uuid) {
        try {
            return userRepository.findByUuid(uuid);
        } catch (Exception e) {
            return null;
        }
    }

    @CacheEvict(value = CacheNames.USERS, allEntries = true)
    public Optional<Users> save(Users user) {
        try {
            final Users persisted = userRepository.persist(user);
            return Optional.of(persisted);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
