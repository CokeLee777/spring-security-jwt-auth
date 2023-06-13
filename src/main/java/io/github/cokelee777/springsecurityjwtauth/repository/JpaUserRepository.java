package io.github.cokelee777.springsecurityjwtauth.repository;

import io.github.cokelee777.springsecurityjwtauth.entity.DBUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<DBUser, Long> {

    boolean existsByIdentifier(String identifier);

    Optional<DBUser> findByIdentifier(String identifier);
}
