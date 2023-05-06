package io.github.cokelee777.springsecurityjwtauth.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record SignUpRequestDto(
        @NotBlank(message = "아이디를 입력해주세요")
        @Email(message = "이메일 형식으로 입력해주세요")
        String identifier,

        @NotBlank(message = "비밀번호를 입력해주세요")
        @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}",
                message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용해주세요")
        String password,

        @Nullable
        String nickname
) {

        @Override
        public String nickname() {
                return (nickname != null) ? nickname : "";
        }
}
