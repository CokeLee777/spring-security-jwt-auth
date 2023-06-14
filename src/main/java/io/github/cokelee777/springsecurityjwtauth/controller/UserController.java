package io.github.cokelee777.springsecurityjwtauth.controller;

import io.github.cokelee777.springsecurityjwtauth.dto.GetProfileResponseDto;
import io.github.cokelee777.springsecurityjwtauth.dto.SignUpRequestDto;
import io.github.cokelee777.springsecurityjwtauth.dto.SignUpResponseDto;
import io.github.cokelee777.springsecurityjwtauth.dto.common.SuccessResponseBody;
import io.github.cokelee777.springsecurityjwtauth.entity.User;
import io.github.cokelee777.springsecurityjwtauth.enums.UserRole;
import io.github.cokelee777.springsecurityjwtauth.security.auth.JwtUserDetails;
import io.github.cokelee777.springsecurityjwtauth.service.UserService;
import io.github.cokelee777.springsecurityjwtauth.utils.DefaultHttpMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<SuccessResponseBody<SignUpResponseDto>> signUp(@Valid @RequestBody SignUpRequestDto signUpRequestDto) {
        User user = userService.createUser(signUpRequestDto);
        SignUpResponseDto signUpResponseDto = new SignUpResponseDto(
                user.getId(), user.getIdentifier(), user.getNickname(), user.getRole());
        return ResponseEntity.ok()
                .body(new SuccessResponseBody<>(
                        HttpStatus.OK.name(),
                        DefaultHttpMessage.OK,
                        signUpResponseDto));
    }

    @GetMapping("/profile")
    public ResponseEntity<SuccessResponseBody<GetProfileResponseDto>> getProfile(
            @AuthenticationPrincipal JwtUserDetails jwtUserDetails) {
        GetProfileResponseDto getProfileResponseDto = new GetProfileResponseDto(
                        jwtUserDetails.getUsername(),
                        jwtUserDetails.getNickname(),
                        UserRole.ROLE_USER.name());
        return ResponseEntity.ok()
                .body(new SuccessResponseBody<>(
                        HttpStatus.OK.name(),
                        DefaultHttpMessage.OK, getProfileResponseDto));
    }
}
