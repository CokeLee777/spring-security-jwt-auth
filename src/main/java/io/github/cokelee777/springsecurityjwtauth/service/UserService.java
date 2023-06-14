package io.github.cokelee777.springsecurityjwtauth.service;

import io.github.cokelee777.springsecurityjwtauth.dto.SignUpRequestDto;
import io.github.cokelee777.springsecurityjwtauth.entity.User;

public interface UserService {

    <T extends User> T createUser(SignUpRequestDto signUpRequestDto);

    <T extends User> T getByIdentifier(String identifier);
}
