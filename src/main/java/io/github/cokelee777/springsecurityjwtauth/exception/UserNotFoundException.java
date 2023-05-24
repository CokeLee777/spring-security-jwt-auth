package io.github.cokelee777.springsecurityjwtauth.exception;

// TODO: 인가 과정에서 발생한 오류에 대한 것이기 때문에 후에 예외처리 구현 시 수정 필요
public class UserNotFoundException extends IllegalArgumentException {

    public UserNotFoundException(String s) {
        super(s);
    }
}
