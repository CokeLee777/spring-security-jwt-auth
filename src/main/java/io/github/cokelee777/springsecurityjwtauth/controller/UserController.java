package io.github.cokelee777.springsecurityjwtauth.controller;

import io.github.cokelee777.springsecurityjwtauth.dto.SignUpRequestDto;
import io.github.cokelee777.springsecurityjwtauth.dto.common.SuccessResponseBody;
import io.github.cokelee777.springsecurityjwtauth.service.UserService;
import io.github.cokelee777.springsecurityjwtauth.utils.DefaultHttpMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService memoryUserService;

    @PostMapping("/sign-up")
    public ResponseEntity<SuccessResponseBody<Void>> signUp(@Valid @RequestBody SignUpRequestDto signUpRequestDto) {
        memoryUserService.createUser(signUpRequestDto);
        return ResponseEntity.ok()
                .body(new SuccessResponseBody<>(HttpStatus.OK.name(), DefaultHttpMessage.OK, null));
    }
}
