package com.backend.esignature.services.persistence;

import com.backend.esignature.entities.Role;
import com.backend.esignature.entities.Users;
import com.backend.esignature.repositories.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    @Lazy
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return User.builder()
                .username(user.getUsername())
                .password(user.getPasswordHash())
                .authorities(user.getRoles().stream()
                        .map(role -> "ROLE_" + role.getName())
                        .toArray(String[]::new))
                .accountExpired(!user.isActive())
                .accountLocked(!user.isActive())
                .credentialsExpired(false)
                .disabled(!user.isActive())
                .build();
    }

    @Transactional
    public Users createUser(String username, String email, String password, String fullName) {
        Users user = Users.builder()
                .username(username)
                .email(email)
                .passwordHash(passwordEncoder.encode(password))
                .fullName(fullName)
                .isActive(true)
                .build();
        Role role = roleService.getOrCreateDefaultUserRole();
        user.getRoles().add(role);

        return userRepository.save(user);
    }

    @Transactional
    public void assignRoleToUser(String userId, String roleName){
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with userId: " + userId));

        Role role = roleService.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found with name " + roleName));
        user.getRoles().add(role);
        userRepository.save(user);
    }

    @Transactional
    public void removeRoleFromUser(String userId, String roleName) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with userId: " + userId));

        Role role = roleService.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found with name: " + roleName));

        user.removeRole(role);
        userRepository.save(user);
        log.info("Removed role {} from user {}", roleName, user.getUsername());
    }

    @Transactional(readOnly = true)
    public Optional<Users> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional(readOnly = true)
    public Optional<Users> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public Optional<Users> findById(String id) {
        return userRepository.findById(id);
    }

    @Transactional
    public Users updateUser(Users user) {
        return userRepository.save(user);
    }

    @Transactional
    public void activateUser(String userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with userId: " + userId));

        user.setActive(true);
        userRepository.save(user);
        log.info("Activated user: {}", user.getUsername());
    }

    @Transactional
    public void deactivateUser(String userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with userId: " + userId));

        user.setActive(false);
        userRepository.save(user);
        log.info("Deactivated user: {}", user.getUsername());
    }

    @Transactional
    public void updatePassword(String userId, String newPassword) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with userId: " + userId));

        user.setPasswordHash(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        log.info("Password updated for user: {}", user.getUsername());
    }

    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
