package com.caroarias.tarea1.auth.controller;

import com.caroarias.tarea1.auth.models.dtos.LoginRequestDTO;
import com.caroarias.tarea1.auth.models.dtos.LoginResponseDTO;
import com.caroarias.tarea1.auth.models.dtos.UserDTO;
import com.caroarias.tarea1.auth.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        return authenticationService.login(loginRequest);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> me() {
        return authenticationService.me();
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout() {
        return authenticationService.logout();
    }
}