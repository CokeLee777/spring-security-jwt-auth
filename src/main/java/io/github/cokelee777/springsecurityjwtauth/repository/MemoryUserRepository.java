package io.github.cokelee777.springsecurityjwtauth.repository;

import io.github.cokelee777.springsecurityjwtauth.domain.MemoryUser;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Primary
@Repository
public class MemoryUserRepository implements UserRepository<MemoryUser> {

    // 동시성 관리를 위해 스레드 안전한 ConcurrentHashMap 사용
    private static final Map<String, MemoryUser> store = new ConcurrentHashMap<>();

    @Override
    public void save(MemoryUser user) {
        store.put(user.getId(), user);
    }

    @Override
    public boolean existsByIdentifier(String identifier) {
        return store.values().stream()
                .anyMatch(user -> user.getIdentifier().equals(identifier));
    }
}
