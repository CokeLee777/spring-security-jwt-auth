package io.github.cokelee777.springsecurityjwtauth.repository;

import io.github.cokelee777.springsecurityjwtauth.annotations.DataBase;
import io.github.cokelee777.springsecurityjwtauth.entity.DBUser;
import io.github.cokelee777.springsecurityjwtauth.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@DataBase
public class DBUserRepository implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    public DBUserRepository(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }


    @Override
    @SuppressWarnings("unchecked")
    public <T extends User> T save(T user) {
        DBUser dbUser = (DBUser) user;
        return (T) jpaUserRepository.save(dbUser);
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
