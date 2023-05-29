package io.github.cokelee777.springsecurityjwtauth.controller;

import io.github.cokelee777.springsecurityjwtauth.dto.GetProfileResponseDto;
import io.github.cokelee777.springsecurityjwtauth.dto.SignUpRequestDto;
import io.github.cokelee777.springsecurityjwtauth.dto.common.SuccessResponseBody;
import io.github.cokelee777.springsecurityjwtauth.enums.UserRole;
import io.github.cokelee777.springsecurityjwtauth.security.auth.PrincipalUserDetails;
import io.github.cokelee777.springsecurityjwtauth.service.UserService;
import io.github.cokelee777.springsecurityjwtauth.utils.DefaultHttpMessage;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<SuccessResponseBody<Void>> signUp(@Valid @RequestBody SignUpRequestDto signUpRequestDto) {
        userService.createUser(signUpRequestDto);
        return ResponseEntity.ok()
                .body(new SuccessResponseBody<>(HttpStatus.OK.name(), DefaultHttpMessage.OK, null));
    }

    @GetMapping("/profile")
    public ResponseEntity<SuccessResponseBody<GetProfileResponseDto>> getProfile(
            @AuthenticationPrincipal PrincipalUserDetails principalUserDetails) {
        GetProfileResponseDto getProfileResponseDto =
                new GetProfileResponseDto(principalUserDetails.getUsername(), UserRole.ROLE_USER.name());
        return ResponseEntity.ok()
                .body(new SuccessResponseBody<>(
                        HttpStatus.OK.name(),
                        DefaultHttpMessage.OK, getProfileResponseDto));
    }
}
