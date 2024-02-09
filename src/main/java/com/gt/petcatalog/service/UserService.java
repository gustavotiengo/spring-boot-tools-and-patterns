package com.gt.petcatalog.service;

import com.gt.petcatalog.repository.UserRepository;
import com.gt.petcatalog.tables.pojos.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Users> findAll() {
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

    public Optional<Users> save(Users user) {
        try {
            final Users persisted = userRepository.persist(user);
            return Optional.of(persisted);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
