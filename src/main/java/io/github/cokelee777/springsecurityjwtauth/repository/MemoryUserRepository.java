package io.github.cokelee777.springsecurityjwtauth.repository;

import io.github.cokelee777.springsecurityjwtauth.entity.MemoryUser;
import io.github.cokelee777.springsecurityjwtauth.entity.User;

import java.util.Map;
import java.util.Optional;

public class MemoryUserRepository implements UserRepository {

    private final Map<String, MemoryUser> memoryStore;

    public MemoryUserRepository(Map<String, MemoryUser> memoryStore) {
        this.memoryStore = memoryStore;
    }

    @Override
    public <T extends User> void save(T user) {
        MemoryUser memoryUser = (MemoryUser) user;
        memoryStore.putIfAbsent(memoryUser.getId().toString(), memoryUser);
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
