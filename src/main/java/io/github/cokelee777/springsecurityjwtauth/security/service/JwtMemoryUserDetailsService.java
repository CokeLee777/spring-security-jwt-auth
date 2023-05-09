package io.github.cokelee777.springsecurityjwtauth.security.service;

import io.github.cokelee777.springsecurityjwtauth.domain.MemoryUser;
import io.github.cokelee777.springsecurityjwtauth.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtMemoryUserDetailsService implements JwtUserDetailsService<MemoryUser> {

    private final UserRepository<MemoryUser> userRepository;

    public JwtMemoryUserDetailsService(UserRepository<MemoryUser> userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByIdentifier(username).orElseThrow(
                () -> new UsernameNotFoundException("존재하지 않는 아이디 입니다"));
    }
}
