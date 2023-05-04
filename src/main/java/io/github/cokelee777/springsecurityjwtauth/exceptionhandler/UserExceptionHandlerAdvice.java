package io.github.cokelee777.springsecurityjwtauth.exceptionhandler;

import io.github.cokelee777.springsecurityjwtauth.dto.common.ExceptionResponseBody;
import io.github.cokelee777.springsecurityjwtauth.exception.DuplicateIdentifierException;
import io.github.cokelee777.springsecurityjwtauth.utils.DefaultHttpMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Order(0)
@RestControllerAdvice
public class UserExceptionHandlerAdvice {

    @ExceptionHandler(DuplicateIdentifierException.class)
    public ResponseEntity<ExceptionResponseBody> exceptionHandler(DuplicateIdentifierException exception) {
        log.error("DuplicateIdentifierException={}", exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ExceptionResponseBody(
                        HttpStatus.CONFLICT.name(),
                        DefaultHttpMessage.CONFLICT,
                        exception.getMessage()
                    )
                );
    }
}
