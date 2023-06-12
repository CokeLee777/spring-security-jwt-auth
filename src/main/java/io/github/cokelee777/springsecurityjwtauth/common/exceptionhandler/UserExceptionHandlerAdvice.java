package io.github.cokelee777.springsecurityjwtauth.common.exceptionhandler;

import io.github.cokelee777.springsecurityjwtauth.common.dto.common.ExceptionResponseBody;
import io.github.cokelee777.springsecurityjwtauth.common.exception.DuplicateIdentifierException;
import io.github.cokelee777.springsecurityjwtauth.common.utils.DefaultHttpMessage;
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
