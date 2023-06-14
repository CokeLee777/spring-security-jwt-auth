package io.github.cokelee777.springsecurityjwtauth.service;

import io.github.cokelee777.springsecurityjwtauth.annotations.Memory;
import io.github.cokelee777.springsecurityjwtauth.dto.SignUpRequestDto;
import io.github.cokelee777.springsecurityjwtauth.entity.MemoryUser;
import io.github.cokelee777.springsecurityjwtauth.exception.DuplicateIdentifierException;
import io.github.cokelee777.springsecurityjwtauth.exception.UserNotFoundException;
import io.github.cokelee777.springsecurityjwtauth.repository.MemoryUserRepository;
import io.github.cokelee777.springsecurityjwtauth.service.common.PasswordBcryptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Memory
@Service
@RequiredArgsConstructor
public class MemoryUserService implements UserService {

    private final MemoryUserRepository memoryUserRepository;
    private final PasswordBcryptService passwordBcryptService;

    @Override
    @SuppressWarnings("unchecked")
    public MemoryUser createUser(SignUpRequestDto signUpRequestDto) {
        boolean isDuplicated = memoryUserRepository.existsByIdentifier(signUpRequestDto.identifier());
        if(isDuplicated) {
            throw new DuplicateIdentifierException("중복된 아이디 입니다");
        }

        MemoryUser memoryUser = new MemoryUser(
                signUpRequestDto.identifier(),
                passwordBcryptService.bcryptPassword(signUpRequestDto.password()),
                signUpRequestDto.nickname());
        return memoryUserRepository.save(memoryUser);
    }

    @Override
    @SuppressWarnings("unchecked")
    public MemoryUser getByIdentifier(String identifier) {
        return memoryUserRepository.findByIdentifier(identifier)
                .orElseThrow(() -> new UserNotFoundException("유저 정보를 찾을 수 없습니다."));
    }
}
