package com.caroarias.tarea1.auth.service;

import com.caroarias.tarea1.auth.models.dtos.LoginRequestDTO;
import com.caroarias.tarea1.auth.models.entities.User;
import com.caroarias.tarea1.auth.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Logica del login. Delega la verificacion de credenciales al AuthenticationManager
 * Si las credenciales son invalidas, AuthenticationManager lanza una
 * AuthenticationException que sube hasta el cliente como 401/403.
 */
@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public AuthenticationService(AuthenticationManager authenticationManager,
                                 UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    public User authenticate(LoginRequestDTO loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        return userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }
}
