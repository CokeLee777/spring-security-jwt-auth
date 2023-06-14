package io.github.cokelee777.springsecurityjwtauth.repository;

import io.github.cokelee777.springsecurityjwtauth.entity.User;

import java.util.Optional;

public interface UserRepository {
    <T extends User> T save(T user);

    boolean existsByIdentifier(String identifier);

    <T extends User> Optional<T> findByIdentifier(String identifier);
}
