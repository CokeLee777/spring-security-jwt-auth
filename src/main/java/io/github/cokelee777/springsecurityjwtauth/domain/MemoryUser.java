package io.github.cokelee777.springsecurityjwtauth.domain;

import io.github.cokelee777.springsecurityjwtauth.enums.UserRole;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
public class MemoryUser implements User {

    @Builder.Default
    private final String id = createUUID();
    private final String identifier;
    private final String password;
    private final String nickname;

    @Builder.Default
    private UserRole role = UserRole.USER;

    @Builder
    public MemoryUser(String identifier, String password, String nickname, UserRole role) {
        this.identifier = identifier;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
    }

    private String createUUID(){
        return UUID.randomUUID().toString();
    }
}
