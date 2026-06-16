package com.caroarias.tarea1.auth.controller;

import com.caroarias.tarea1.auth.mappers.UserMapper;
import com.caroarias.tarea1.auth.models.dtos.LoginRequestDTO;
import com.caroarias.tarea1.auth.models.dtos.LoginResponseDTO;
import com.caroarias.tarea1.auth.models.dtos.UserDTO;
import com.caroarias.tarea1.auth.models.entities.User;
import com.caroarias.tarea1.auth.service.AuthenticationService;
import com.caroarias.tarea1.auth.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    public AuthController(AuthenticationService authenticationService,
                          JwtService jwtService,
                          UserMapper userMapper) {
        this.authenticationService = authenticationService;
        this.jwtService = jwtService;
        this.userMapper = userMapper;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        User authenticatedUser = authenticationService.authenticate(loginRequest);
        String token = jwtService.generateToken(authenticatedUser);

        LoginResponseDTO response = new LoginResponseDTO(
                token,
                jwtService.getExpirationTime(),
                userMapper.toDto(authenticatedUser)
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> me() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        return ResponseEntity.ok(userMapper.toDto(user));
    }
}
