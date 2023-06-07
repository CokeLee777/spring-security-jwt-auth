package io.github.cokelee777.springsecurityjwtauth.memory.security.jwt.service;

import io.github.cokelee777.springsecurityjwtauth.memory.entity.MemoryUser;
import io.github.cokelee777.springsecurityjwtauth.memory.repository.MemoryUserRepository;
import io.github.cokelee777.springsecurityjwtauth.memory.security.jwt.auth.JwtMemoryUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtMemoryUserDetailsService implements UserDetailsService {

    private final MemoryUserRepository memoryUserRepository;

    public JwtMemoryUserDetailsService(MemoryUserRepository memoryUserRepository) {
        this.memoryUserRepository = memoryUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemoryUser memoryUser = memoryUserRepository.findByIdentifier(username).orElseThrow(
                () -> new UsernameNotFoundException("존재하지 않는 아이디 입니다"));
        return new JwtMemoryUserDetails(memoryUser);
    }
}
