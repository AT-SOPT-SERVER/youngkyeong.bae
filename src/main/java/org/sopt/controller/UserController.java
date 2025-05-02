package org.sopt.controller;

import org.sopt.dto.request.UserCreateRequest;
import org.sopt.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody UserCreateRequest userCreateRequest) {
        Long id = userService.signup(userCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, "/users/" + id).build();
    }
}
