package io.github.cokelee777.springsecurityjwtauth.entity;

import io.github.cokelee777.springsecurityjwtauth.enums.UserRole;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@EqualsAndHashCode
@RequiredArgsConstructor
public class MemoryUser implements User {

    private String id = createUUID();
    private final String identifier;
    private final String password;
    private final String nickname;
    private UserRole role = UserRole.ROLE_USER;


    public MemoryUser(String identifier, String password, String nickname, UserRole role) {
        this.id = createUUID();
        this.identifier = identifier;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
    }

    @Override
    public Object getId() {
        return id;
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public UserRole getRole() {
        return role;
    }

    @Override
    public String getNickname() {
        return nickname;
    }

    private String createUUID(){
        return UUID.randomUUID().toString();
    }
}
