package io.github.cokelee777.springsecurityjwtauth.service;

import io.github.cokelee777.springsecurityjwtauth.domain.MemoryUser;
import io.github.cokelee777.springsecurityjwtauth.dto.SignUpRequestDto;
import io.github.cokelee777.springsecurityjwtauth.exception.DuplicateIdentifierException;
import io.github.cokelee777.springsecurityjwtauth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemoryUserService implements UserService {

    private final UserRepository<MemoryUser> memoryUserRepository;

    @Override
    public void createUser(SignUpRequestDto signUpRequestDto) {
        boolean isDuplicated = memoryUserRepository.existsByIdentifier(signUpRequestDto.identifier());
        if(isDuplicated) {
            throw new DuplicateIdentifierException("중복된 아이디 입니다");
        }

        String nickname = signUpRequestDto.nickname() == null ? "" : signUpRequestDto.nickname();
        MemoryUser user = new MemoryUser(
                signUpRequestDto.identifier(),
                signUpRequestDto.password(),
                nickname
        );
        memoryUserRepository.save(user);
    }
}
