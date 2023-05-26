package io.github.cokelee777.springsecurityjwtauth.service;

import io.github.cokelee777.springsecurityjwtauth.domain.MemoryUser;
import io.github.cokelee777.springsecurityjwtauth.dto.SignUpRequestDto;
import io.github.cokelee777.springsecurityjwtauth.exception.DuplicateIdentifierException;
import io.github.cokelee777.springsecurityjwtauth.exception.UserNotFoundException;
import io.github.cokelee777.springsecurityjwtauth.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

public class MemoryUserService implements UserService {

    private final UserRepository<MemoryUser> userRepository;
    private final PasswordEncoder passwordEncoder;

    public MemoryUserService(UserRepository<MemoryUser> userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createUser(SignUpRequestDto signUpRequestDto) {
        boolean isDuplicated = userRepository.existsByIdentifier(signUpRequestDto.identifier());
        if(isDuplicated) {
            throw new DuplicateIdentifierException("중복된 아이디 입니다");
        }

        MemoryUser memoryUser = new MemoryUser(
                signUpRequestDto.identifier(),
                getBcryptPassword(signUpRequestDto.password()),
                signUpRequestDto.nickname()
        );
        userRepository.save(memoryUser);
    }

    public MemoryUser findByIdentifier(String identifier) {
        return userRepository.findByIdentifier(identifier)
                .orElseThrow(() -> new UserNotFoundException("유저 정보를 찾을 수 없습니다."));
    }

    private String getBcryptPassword(String originalPassword) {
        return passwordEncoder.encode(originalPassword);
    }
}
