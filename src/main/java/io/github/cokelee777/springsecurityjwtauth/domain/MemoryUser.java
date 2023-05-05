package io.github.cokelee777.springsecurityjwtauth.domain;

import io.github.cokelee777.springsecurityjwtauth.enums.UserRole;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Getter
@EqualsAndHashCode
public class MemoryUser implements User {

    @Builder.Default
    private final String id = createUUID();
    private final String identifier;
    private final String password;
    private final String nickname;
    private final UserRole role;

    @Builder
    public MemoryUser(String identifier, String password, String nickname, UserRole role) {
        this.identifier = identifier;
        this.password = password;
        this.nickname = nickname;
        this.role = (role == null) ? UserRole.USER : role;
    }

    private String createUUID(){
        return UUID.randomUUID().toString();
    }
}
