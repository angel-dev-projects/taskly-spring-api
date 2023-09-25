package com.angeldevprojects.taskly.controllers;

import com.angeldevprojects.taskly.config.jwt.UserAuthProvider;
import com.angeldevprojects.taskly.dtos.CredentialsDto;
import com.angeldevprojects.taskly.dtos.SignUpDto;
import com.angeldevprojects.taskly.dtos.UserDto;
import com.angeldevprojects.taskly.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v0/auth")
public class AuthController {
    private final UserService userService;
    private final UserAuthProvider userAuthProvider;

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody @Valid CredentialsDto credentialsDto) {
        UserDto userDto = userService.login(credentialsDto);
        userDto.setToken(userAuthProvider.createToken(userDto));
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody @Valid SignUpDto signUpDto) {
        UserDto userDto = userService.register(signUpDto);
        userDto.setToken(userAuthProvider.createToken(userDto));
        return ResponseEntity.created(URI.create("/users/"+ userDto.getId())).body(userDto);
    }
}
