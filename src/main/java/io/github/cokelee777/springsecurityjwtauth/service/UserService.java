package io.github.cokelee777.springsecurityjwtauth.service;

import io.github.cokelee777.springsecurityjwtauth.dto.SignUpRequestDto;
import io.github.cokelee777.springsecurityjwtauth.entity.User;

public interface UserService {

    void createUser(SignUpRequestDto signUpRequestDto);

    User getByIdentifier(String identifier);
}
