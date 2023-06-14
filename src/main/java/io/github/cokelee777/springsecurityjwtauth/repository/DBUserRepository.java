package io.github.cokelee777.springsecurityjwtauth.repository;

import io.github.cokelee777.springsecurityjwtauth.annotations.DataBase;
import io.github.cokelee777.springsecurityjwtauth.entity.DBUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@DataBase
@Repository
@RequiredArgsConstructor
public class DBUserRepository implements UserRepository<DBUser> {

    private final JpaUserRepository jpaUserRepository;

    @Override
    public DBUser save(DBUser user) {
        return jpaUserRepository.save(user);
    }

    @Override
    public boolean existsByIdentifier(String identifier) {
        return jpaUserRepository.existsByIdentifier(identifier);
    }

    @Override
    public Optional<DBUser> findByIdentifier(String identifier) {
        return jpaUserRepository.findByIdentifier(identifier);
    }
}
