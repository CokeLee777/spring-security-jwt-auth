package io.github.cokelee777.springsecurityjwtauth.domain;

import io.github.cokelee777.springsecurityjwtauth.enums.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class MemoryUser implements User {

    private final String id = createUUID();
    private final String identifier;
    private final String password;
    private final String nickname;
    private UserRole role = UserRole.USER;

    public MemoryUser(String identifier, String password, String nickname) {
        this.identifier = identifier;
        this.password = password;
        this.nickname = nickname;
    }

    public MemoryUser(String identifier, String password, String nickname, UserRole role) {
        this.identifier = identifier;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public String getIdentifier() {
        return identifier;
    }

    private String createUUID(){
        return UUID.randomUUID().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemoryUser that = (MemoryUser) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(identifier, that.identifier) &&
                Objects.equals(password, that.password) &&
                Objects.equals(nickname, that.nickname) &&
                role == that.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, identifier, password, nickname, role);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.toString()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.identifier;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
