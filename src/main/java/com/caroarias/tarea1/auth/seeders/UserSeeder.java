package com.caroarias.tarea1.auth.seeders;

import com.caroarias.tarea1.auth.models.entities.Role;
import com.caroarias.tarea1.auth.models.entities.User;
import com.caroarias.tarea1.auth.models.enums.RoleEnum;
import com.caroarias.tarea1.auth.repository.RoleRepository;
import com.caroarias.tarea1.auth.repository.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Inserta dos usuarios fijos en la BD al arrancar la aplicacion, uno por rol:
 * Los passwords se guardan encriptados con BCrypt.
 * Si el usuario ya existe (por email) no se vuelve a crear
 */
@Order(2)
@Component
public class UserSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserSeeder(UserRepository userRepository,
                      RoleRepository roleRepository,
                      PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        createUserIfNotExists(
                "super.admin@example.com",
                "Super",
                "Admin",
                "superadmin123",
                RoleEnum.SUPER_ADMIN_ROLE.getDbName()
        );

        createUserIfNotExists(
                "user@example.com",
                "Regular",
                "User",
                "user123",
                RoleEnum.USER.getDbName()
        );
    }

    private void createUserIfNotExists(String email,
                                       String name,
                                       String lastname,
                                       String rawPassword,
                                       String roleName) {
        if (userRepository.existsByEmail(email)) {
            return;
        }

        Role role = roleRepository.findByName(roleName).orElse(null);
        if (role == null) {
            // El RoleSeeder corre antes; si llegamos aqui hay un problema de
            // configuracion y no tiene sentido crear el usuario sin rol.
            return;
        }

        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setLastname(lastname);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole(role);
        userRepository.save(user);
    }
}
