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
import com.backend.esignature.dto.responses.UserResponse;
import com.backend.esignature.mapper.UserMapper;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final UserMapper userMapper;

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
    public UserResponse findByUsername(String username) {
        try{
            if(username == null || username.isEmpty()){
                throw new IllegalArgumentException("Username cannot be null or empty");
            }
            Users user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
            return userMapper.toResponse(user);
        }catch(Exception e){
            log.error("Error finding user by username {}: {}", username, e.getMessage());
            throw new RuntimeException("Error finding user by username: " + username);
        }
    }

    @Transactional(readOnly = true)
    public UserResponse findByEmail(String email) {
        try{

            if(email == null || email.isEmpty()){
                throw new IllegalArgumentException("Email cannot be null or empty");
            }

            Users user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

            return userMapper.toResponse(user);

        }catch(RuntimeException ex){
            log.error("Error finding user by email {}: {}", email, ex.getMessage());
            throw new RuntimeException("Error finding user by email: " + email);
        }
    }

    @Transactional(readOnly = true)
    public UserResponse findById(String id) {
        try{
            if( id == null || id.isEmpty()){
                throw new IllegalArgumentException("ID cannot be null or empty");
            }

            Users user = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
            return userMapper.toResponse(user);

        }catch( RuntimeException ex){
            log.error("Error finding user by id {}: {}", id, ex.getMessage());
            throw new RuntimeException("Error finding user by id: " + id);
        }
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
