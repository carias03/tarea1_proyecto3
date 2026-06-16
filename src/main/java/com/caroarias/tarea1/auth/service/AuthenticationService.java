package com.caroarias.tarea1.auth.service;

import com.caroarias.tarea1.auth.mappers.UserMapper;
import com.caroarias.tarea1.auth.models.dtos.LoginRequestDTO;
import com.caroarias.tarea1.auth.models.dtos.LoginResponseDTO;
import com.caroarias.tarea1.auth.models.dtos.UserDTO;
import com.caroarias.tarea1.auth.models.entities.User;
import com.caroarias.tarea1.auth.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    public AuthenticationService(AuthenticationManager authenticationManager,
                                 UserRepository userRepository,
                                 JwtService jwtService,
                                 UserMapper userMapper) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.userMapper = userMapper;
    }

    public ResponseEntity<LoginResponseDTO> login(LoginRequestDTO loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        String token = jwtService.generateToken(user);

        LoginResponseDTO response = new LoginResponseDTO(
                token,
                jwtService.getExpirationTime(),
                userMapper.toDto(user)
        );

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<UserDTO> me() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        return ResponseEntity.ok(userMapper.toDto(user));
    }
}