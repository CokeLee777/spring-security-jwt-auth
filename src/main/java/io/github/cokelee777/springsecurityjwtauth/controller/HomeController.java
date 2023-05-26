package io.github.cokelee777.springsecurityjwtauth.controller;

import io.github.cokelee777.springsecurityjwtauth.dto.common.SuccessResponseBody;
import io.github.cokelee777.springsecurityjwtauth.utils.DefaultHttpMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public ResponseEntity<SuccessResponseBody<Void>> home() {
        return ResponseEntity.ok()
                .body(new SuccessResponseBody<>(HttpStatus.OK.name(), DefaultHttpMessage.OK, null));
    }
}
