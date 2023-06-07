package io.github.cokelee777.springsecurityjwtauth.common.repository;

import io.github.cokelee777.springsecurityjwtauth.common.entity.User;

import java.util.Optional;

public interface UserRepository<T extends User> {
    void save(T user);

    boolean existsByIdentifier(String identifier);

    Optional<T> findByIdentifier(String identifier);
}
