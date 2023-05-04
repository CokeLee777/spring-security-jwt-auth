package io.github.cokelee777.springsecurityjwtauth.exceptionhandler;

import io.github.cokelee777.springsecurityjwtauth.dto.common.ExceptionResponseBody;
import io.github.cokelee777.springsecurityjwtauth.utils.DefaultHttpMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Optional;

@Slf4j
@Order(1)
@RestControllerAdvice
public class CommonExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Optional<FieldError> fieldError = Optional.ofNullable(ex.getBindingResult().getFieldError());

        String message = DefaultHttpMessage.BAD_REQUEST;
        if(fieldError.isPresent()) {
            message = fieldError.get().getDefaultMessage();
        }

        log.error("MethodArgumentNotValidException={}", message);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponseBody(
                        HttpStatus.BAD_REQUEST.name(),
                        DefaultHttpMessage.BAD_REQUEST,
                        message
                    )
                );
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("HttpRequestMethodNotSupportedException={}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new ExceptionResponseBody(
                        HttpStatus.METHOD_NOT_ALLOWED.name(),
                        DefaultHttpMessage.METHOD_NOT_ALLOWED,
                        ex.getMessage()
                    )
                );
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("NoHandlerFoundException={}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionResponseBody(
                        HttpStatus.NOT_FOUND.name(),
                        DefaultHttpMessage.NOT_FOUND,
                        ex.getMessage()
                    )
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseBody> exceptionHandler(Exception ex) {
        log.error("Exception={}", ex.getMessage());
        return ResponseEntity
                .internalServerError()
                .body(new ExceptionResponseBody(
                        HttpStatus.INTERNAL_SERVER_ERROR.name(),
                        DefaultHttpMessage.INTERNAL_SERVER_ERROR,
                        ex.getMessage()
                    )
                );
    }
}
