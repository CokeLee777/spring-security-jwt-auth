package io.github.cokelee777.springsecurityjwtauth.repository;

import io.github.cokelee777.springsecurityjwtauth.entity.DBUser;
import io.github.cokelee777.springsecurityjwtauth.entity.User;

import java.util.Optional;

public class DBUserRepository implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    public DBUserRepository(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }


    @Override
    public <T extends User> void save(T user) {
        jpaUserRepository.save((DBUser) user);
    }

    @Override
    public boolean existsByIdentifier(String identifier) {
        return jpaUserRepository.existsByIdentifier(identifier);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends User> Optional<T> findByIdentifier(String identifier) {
        return (Optional<T>) jpaUserRepository.findByIdentifier(identifier);
    }
}
