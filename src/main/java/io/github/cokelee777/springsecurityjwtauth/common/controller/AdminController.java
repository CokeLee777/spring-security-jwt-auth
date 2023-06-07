package io.github.cokelee777.springsecurityjwtauth.common.controller;

import io.github.cokelee777.springsecurityjwtauth.common.dto.common.SuccessResponseBody;
import io.github.cokelee777.springsecurityjwtauth.common.utils.DefaultHttpMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/dashboard")
    public ResponseEntity<SuccessResponseBody<String>> getProfile() {
        return ResponseEntity.ok()
                .body(new SuccessResponseBody<>(HttpStatus.OK.name(), DefaultHttpMessage.OK, "admin dashboard"));
    }
}
