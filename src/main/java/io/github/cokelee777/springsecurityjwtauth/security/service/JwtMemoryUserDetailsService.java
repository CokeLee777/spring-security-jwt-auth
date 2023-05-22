package io.github.cokelee777.springsecurityjwtauth.security.service;

import io.github.cokelee777.springsecurityjwtauth.domain.MemoryUser;
import io.github.cokelee777.springsecurityjwtauth.repository.UserRepository;
import io.github.cokelee777.springsecurityjwtauth.security.auth.JwtMemoryUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class JwtMemoryUserDetailsService implements JwtUserDetailsService<MemoryUser> {

    private final UserRepository<MemoryUser> userRepository;

    public JwtMemoryUserDetailsService(UserRepository<MemoryUser> userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemoryUser memoryUser = userRepository.findByIdentifier(username).orElseThrow(
                () -> new UsernameNotFoundException("존재하지 않는 아이디 입니다"));
        return new JwtMemoryUserDetails(memoryUser);
    }
}
