package io.github.cokelee777.springsecurityjwtauth.entity;

import io.github.cokelee777.springsecurityjwtauth.enums.UserRole;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@RequiredArgsConstructor
public class DBUser implements User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(name = "identifier")
    private final String identifier;

    @Column(name = "password")
    private final String password;

    @Column(name = "nickname")
    private final String nickname;

    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.ROLE_USER;

    public DBUser(String identifier, String password, String nickname, UserRole role) {
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
    public String getNickname() {
        return nickname;
    }

    @Override
    public UserRole getRole() {
        return role;
    }
}
