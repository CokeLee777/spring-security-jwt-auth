package io.github.cokelee777.springsecurityjwtauth.memory.security.jwt.auth;

import io.github.cokelee777.springsecurityjwtauth.memory.entity.MemoryUser;
import io.github.cokelee777.springsecurityjwtauth.common.enums.UserRole;
import io.github.cokelee777.springsecurityjwtauth.memory.security.common.auth.PrincipalUserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

public class JwtMemoryUserDetails implements PrincipalUserDetails {

    private final MemoryUser memoryUser;

    public JwtMemoryUserDetails(MemoryUser memoryUser) {
        this.memoryUser = memoryUser;
    }

    public String getId() {
        return memoryUser.getId();
    }

    public String getNickname() {
        return memoryUser.getNickname();
    }

    public UserRole getRole() {
        return memoryUser.getRole();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(memoryUser.getRole().toString()));
    }

    @Override
    public String getPassword() {
        return memoryUser.getPassword();
    }

    @Override
    public String getUsername() {
        return memoryUser.getIdentifier();
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
