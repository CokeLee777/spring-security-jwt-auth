package io.github.cokelee777.springsecurityjwtauth.repository;

import io.github.cokelee777.springsecurityjwtauth.annotations.Memory;
import io.github.cokelee777.springsecurityjwtauth.entity.MemoryUser;
import io.github.cokelee777.springsecurityjwtauth.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Memory
@Repository
@RequiredArgsConstructor
public class MemoryUserRepository implements UserRepository {

    private final Map<String, MemoryUser> memoryStore;

    @Override
    @SuppressWarnings("unchecked")
    public <T extends User> T save(T user) {
        MemoryUser memoryUser = (MemoryUser) user;
        return (T) memoryStore.putIfAbsent(memoryUser.getId().toString(), memoryUser);
    }

    @Override
    public boolean existsByIdentifier(String identifier) {
        return memoryStore.values().stream()
                .anyMatch(user -> user.getIdentifier().equals(identifier));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends User> Optional<T> findByIdentifier(String identifier) {
        return (Optional<T>) memoryStore.values().stream()
            .filter(user -> user.getIdentifier().equals(identifier))
            .findFirst();
    }
}
