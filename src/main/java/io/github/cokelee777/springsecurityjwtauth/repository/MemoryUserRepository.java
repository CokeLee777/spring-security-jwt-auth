package io.github.cokelee777.springsecurityjwtauth.repository;

import io.github.cokelee777.springsecurityjwtauth.domain.MemoryUser;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Primary
@Repository
public class MemoryUserRepository implements UserRepository<MemoryUser> {

    private final Map<String, MemoryUser> memoryStore;

    public MemoryUserRepository(Map<String, MemoryUser> memoryStore) {
        this.memoryStore = memoryStore;
    }

    @Override
    public void save(MemoryUser user) {
        memoryStore.putIfAbsent(user.getId(), user);
    }

    @Override
    public boolean existsByIdentifier(String identifier) {
        return memoryStore.values().stream()
                .anyMatch(user -> user.getIdentifier().equals(identifier));
    }
}
