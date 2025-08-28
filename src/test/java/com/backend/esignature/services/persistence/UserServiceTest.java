package com.backend.esignature.services.persistence;

import com.backend.esignature.dto.requests.UserRequest;
import com.backend.esignature.dto.responses.UserResponse;
import com.backend.esignature.entities.Role;
import com.backend.esignature.entities.Users;
import com.backend.esignature.mapper.UserMapper;
import com.backend.esignature.repositories.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private RoleService roleService;
    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByUsername_Success() {
        Users user = Users.builder().username("testuser").isActive(true).build();
        UserResponse userResponse = new UserResponse();
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(userMapper.toResponse(user)).thenReturn(userResponse);
        UserResponse result = userService.findByUsername("testuser");
        assertEquals(userResponse, result);
    }

    @Test
    void testCreateUser_Success() {
        UserRequest req = new UserRequest();
        req.setUsername("testuser");
        req.setEmail("test@email.com");
        req.setPassword("pass");
        req.setFullName("Test User");
        Role role = new Role();
        when(passwordEncoder.encode("pass")).thenReturn("hashed");
        when(roleService.getOrCreateDefaultUserRole()).thenReturn(role);
        when(userRepository.save(any(Users.class))).thenAnswer(i -> i.getArguments()[0]);
        Users created = userService.createUser(req);
        assertEquals("testuser", created.getUsername());
        assertEquals("hashed", created.getPasswordHash());
        assertTrue(created.getRoles().contains(role));
    }

    @Test
    void testExistsByUsername() {
        when(userRepository.existsByUsername("testuser")).thenReturn(true);
        assertTrue(userService.existsByUsername("testuser"));
    }

    @Test
    void testExistsByEmail() {
        when(userRepository.existsByEmail("test@email.com")).thenReturn(true);
        assertTrue(userService.existsByEmail("test@email.com"));
    }
}
