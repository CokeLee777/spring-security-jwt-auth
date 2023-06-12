package io.github.cokelee777.springsecurityjwtauth.memory.service;

import io.github.cokelee777.springsecurityjwtauth.common.dto.SignUpRequestDto;
import io.github.cokelee777.springsecurityjwtauth.common.exception.DuplicateIdentifierException;
import io.github.cokelee777.springsecurityjwtauth.common.exception.UserNotFoundException;
import io.github.cokelee777.springsecurityjwtauth.common.service.UserService;
import io.github.cokelee777.springsecurityjwtauth.memory.entity.MemoryUser;
import io.github.cokelee777.springsecurityjwtauth.memory.repository.MemoryUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemoryUserService implements UserService {

    private final MemoryUserRepository memoryUserRepository;
    private final PasswordEncoder passwordEncoder;

    public MemoryUserService(MemoryUserRepository memoryUserRepository, PasswordEncoder passwordEncoder) {
        this.memoryUserRepository = memoryUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createUser(SignUpRequestDto signUpRequestDto) {
        boolean isDuplicated = memoryUserRepository.existsByIdentifier(signUpRequestDto.identifier());
        if(isDuplicated) {
            throw new DuplicateIdentifierException("중복된 아이디 입니다");
        }

        MemoryUser memoryUser = new MemoryUser(
                signUpRequestDto.identifier(),
                getBcryptPassword(signUpRequestDto.password()),
                signUpRequestDto.nickname()
        );
        memoryUserRepository.save(memoryUser);
    }

    public MemoryUser findByIdentifier(String identifier) {
        return memoryUserRepository.findByIdentifier(identifier)
                .orElseThrow(() -> new UserNotFoundException("유저 정보를 찾을 수 없습니다."));
    }

    private String getBcryptPassword(String originalPassword) {
        return passwordEncoder.encode(originalPassword);
    }
}
