package com.caroarias.tarea1.auth.seeders;

import com.caroarias.tarea1.auth.models.entities.Role;
import com.caroarias.tarea1.auth.models.enums.RoleEnum;
import com.caroarias.tarea1.auth.repository.RoleRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Inserta los dos roles requeridos si aun no existen en la BD:
 *   - "SUPER-ADMIN-ROLE"
 *   - "USER"
 * Se ejecuta @Order(1) para garantizar que los roles existan antes de que
 * UserSeeder (@Order(2)) intente asignarlos a los usuarios sembrados.
 */
@Order(1)
@Component
public class RoleSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final RoleRepository roleRepository;

    public RoleSeeder(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        for (RoleEnum roleEnum : RoleEnum.values()) {
            String name = roleEnum.getDbName();
            if (roleRepository.existsByName(name)) {
                continue;
            }

            Role role = new Role();
            role.setName(name);
            role.setDescription(buildDescription(roleEnum));
            roleRepository.save(role);
        }
    }

    private String buildDescription(RoleEnum roleEnum) {
        return switch (roleEnum) {
            case SUPER_ADMIN_ROLE -> "Super administrador del sistema";
            case USER -> "Usuario regular del sistema";
        };
    }
}
