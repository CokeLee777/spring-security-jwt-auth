package io.github.cokelee777.springsecurityjwtauth.security.service;

import io.github.cokelee777.springsecurityjwtauth.entity.User;
import io.github.cokelee777.springsecurityjwtauth.repository.UserRepository;
import io.github.cokelee777.springsecurityjwtauth.security.auth.JwtUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByIdentifier(username).orElseThrow(
                () -> new UsernameNotFoundException("존재하지 않는 아이디 입니다"));
        return new JwtUserDetails(user);
    }
}
