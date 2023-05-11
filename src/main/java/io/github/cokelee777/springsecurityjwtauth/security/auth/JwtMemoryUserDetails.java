package io.github.cokelee777.springsecurityjwtauth.security.auth;

import io.github.cokelee777.springsecurityjwtauth.domain.MemoryUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

public class JwtMemoryUserDetails implements JwtUserDetails<MemoryUser> {

    private final MemoryUser memoryUser;

    public JwtMemoryUserDetails(MemoryUser memoryUser) {
        this.memoryUser = memoryUser;
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
