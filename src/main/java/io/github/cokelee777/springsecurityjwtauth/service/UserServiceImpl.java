package io.github.cokelee777.springsecurityjwtauth.service;

import io.github.cokelee777.springsecurityjwtauth.dto.SignUpRequestDto;
import io.github.cokelee777.springsecurityjwtauth.entity.DBUser;
import io.github.cokelee777.springsecurityjwtauth.entity.User;
import io.github.cokelee777.springsecurityjwtauth.exception.DuplicateIdentifierException;
import io.github.cokelee777.springsecurityjwtauth.exception.UserNotFoundException;
import io.github.cokelee777.springsecurityjwtauth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void createUser(SignUpRequestDto signUpRequestDto) {
        boolean isDuplicated = userRepository.existsByIdentifier(signUpRequestDto.identifier());
        if(isDuplicated) {
            throw new DuplicateIdentifierException("중복된 아이디 입니다");
        }

        DBUser dbUser = new DBUser(
                signUpRequestDto.identifier(),
                getBcryptPassword(signUpRequestDto.password()),
                signUpRequestDto.nickname());
        userRepository.save(dbUser);
    }

    @Override
    public User getByIdentifier(String identifier) {
        return userRepository.findByIdentifier(identifier)
            .orElseThrow(() -> new UserNotFoundException("유저 정보를 찾을 수 없습니다."));
    }

    private String getBcryptPassword(String originalPassword) {
        return passwordEncoder.encode(originalPassword);
    }
}
