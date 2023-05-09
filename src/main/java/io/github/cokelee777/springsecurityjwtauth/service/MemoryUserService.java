package io.github.cokelee777.springsecurityjwtauth.service;

import io.github.cokelee777.springsecurityjwtauth.domain.MemoryUser;
import io.github.cokelee777.springsecurityjwtauth.dto.SignUpRequestDto;
import io.github.cokelee777.springsecurityjwtauth.exception.DuplicateIdentifierException;
import io.github.cokelee777.springsecurityjwtauth.repository.UserRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Primary
@Service
public class MemoryUserService implements UserService {

    private final UserRepository<MemoryUser> userRepository;
    private final PasswordEncoder bcryptPasswordEncoder;

    public MemoryUserService(UserRepository<MemoryUser> userRepository, PasswordEncoder bcryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bcryptPasswordEncoder = bcryptPasswordEncoder;
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

    private String getBcryptPassword(String originalPassword) {
        return bcryptPasswordEncoder.encode(originalPassword);
    }
}
