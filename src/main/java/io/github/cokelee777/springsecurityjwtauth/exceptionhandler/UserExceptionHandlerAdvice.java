package io.github.cokelee777.springsecurityjwtauth.exceptionhandler;

import io.github.cokelee777.springsecurityjwtauth.dto.common.ExceptionResponseBody;
import io.github.cokelee777.springsecurityjwtauth.exception.DuplicateIdentifierException;
import io.github.cokelee777.springsecurityjwtauth.utils.DefaultHttpMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(0)
@RestControllerAdvice
public class UserExceptionHandlerAdvice {

    private final Logger log = LoggerFactory.getLogger(getClass());

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
