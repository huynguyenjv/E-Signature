package com.backend.authservice.services;


import com.backend.authservice.entities.Role;
import com.backend.authservice.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }

    public Role createRoleIfNotExists(String roleName, String description) {
        return roleRepository.findByName(roleName)
                .orElseGet(() -> {
                    Role role = Role.builder()
                            .name(roleName)
                            .description(description)
                            .build();
                    return roleRepository.save(role);
                });
    }

    public Role getOrCreateDefaultUserRole() {
        return createRoleIfNotExists("USER", "Default user role");
    }

    public Role getOrCreateAdminRole() {
        return createRoleIfNotExists("ADMIN", "Administrator role");
    }
}
