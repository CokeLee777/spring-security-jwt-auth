package io.github.cokelee777.springsecurityjwtauth.entity;

import io.github.cokelee777.springsecurityjwtauth.enums.UserRole;
import jakarta.persistence.*;

@Entity
public class DBUser implements User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(name = "identifier")
    private String identifier;

    @Column(name = "password")
    private String password;

    @Column(name = "nickname")
    private String nickname;

    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.ROLE_USER;

    protected DBUser() { }

    public DBUser(String identifier, String password, String nickname) {
        this.identifier = identifier;
        this.password = password;
        this.nickname = nickname;
    }

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
