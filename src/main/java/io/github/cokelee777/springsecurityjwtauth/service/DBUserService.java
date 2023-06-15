package io.github.cokelee777.springsecurityjwtauth.service;

import io.github.cokelee777.springsecurityjwtauth.annotations.DataBase;
import io.github.cokelee777.springsecurityjwtauth.dto.SignUpRequestDto;
import io.github.cokelee777.springsecurityjwtauth.dto.SignUpResponseDto;
import io.github.cokelee777.springsecurityjwtauth.entity.DBUser;
import io.github.cokelee777.springsecurityjwtauth.exception.DuplicateIdentifierException;
import io.github.cokelee777.springsecurityjwtauth.exception.UserNotFoundException;
import io.github.cokelee777.springsecurityjwtauth.repository.DBUserRepository;
import io.github.cokelee777.springsecurityjwtauth.service.common.PasswordBcryptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@DataBase
@Service
@RequiredArgsConstructor
public class DBUserService implements UserService {

    private final DBUserRepository dbUserRepository;

    @Override
    public SignUpResponseDto createUser(SignUpRequestDto signUpRequestDto) {
        boolean isDuplicated = dbUserRepository.existsByIdentifier(signUpRequestDto.identifier());
        if(isDuplicated) {
            throw new DuplicateIdentifierException("중복된 아이디 입니다");
        }

        DBUser dbUser = new DBUser(
                signUpRequestDto.identifier(),
                PasswordBcryptService.bcryptPassword(signUpRequestDto.password()),
                signUpRequestDto.nickname());
        DBUser savedDBUser = dbUserRepository.save(dbUser);
        return SignUpResponseDto.fromUser(savedDBUser);
    }

    @Override
    @SuppressWarnings("unchecked")
    public DBUser getByIdentifier(String identifier) {
        return dbUserRepository.findByIdentifier(identifier)
            .orElseThrow(() -> new UserNotFoundException("유저 정보를 찾을 수 없습니다."));
    }
}
