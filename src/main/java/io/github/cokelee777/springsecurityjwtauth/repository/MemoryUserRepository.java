package io.github.cokelee777.springsecurityjwtauth.repository;

import io.github.cokelee777.springsecurityjwtauth.annotations.Memory;
import io.github.cokelee777.springsecurityjwtauth.entity.MemoryUser;
import io.github.cokelee777.springsecurityjwtauth.exception.DuplicatedUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Memory
@Repository
@RequiredArgsConstructor
public class MemoryUserRepository implements UserRepository<MemoryUser> {

    private final Map<String, MemoryUser> memoryStore;

    @Override
    public MemoryUser save(MemoryUser user) {
        MemoryUser oldMemoryUser = memoryStore.putIfAbsent(user.getId().toString(), user);
        if(oldMemoryUser != null) throw new DuplicatedUserException("이미 존재하는 사용자 입니다");
        return user;
    }

    @Override
    public boolean existsByIdentifier(String identifier) {
        return memoryStore.values().stream()
                .anyMatch(user -> user.getIdentifier().equals(identifier));
    }

    @Override
    public Optional<MemoryUser> findByIdentifier(String identifier) {
        return memoryStore.values().stream()
            .filter(user -> user.getIdentifier().equals(identifier))
            .findFirst();
    }
}
