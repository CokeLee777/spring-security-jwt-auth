package io.github.cokelee777.springsecurityjwtauth.repository;

import io.github.cokelee777.springsecurityjwtauth.domain.MemoryUser;

import java.util.Map;
import java.util.Optional;

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

    @Override
    public Optional<MemoryUser> findByIdentifier(String identifier) {
        return memoryStore.values().stream()
                .filter(user -> user.getIdentifier().equals(identifier))
                .findFirst();
    }
}
