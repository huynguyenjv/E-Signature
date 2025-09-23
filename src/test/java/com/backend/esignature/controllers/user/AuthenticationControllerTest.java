package com.backend.esignature.controllers.user;

import com.backend.esignature.dto.requests.LoginRequest;
import com.backend.esignature.dto.requests.RegisterRequest;
import com.backend.esignature.dto.requests.RefreshTokenRequest;
import com.backend.esignature.dto.responses.AuthResponse;
import com.backend.esignature.services.business.AuthenticationService;
import com.backend.esignature.config.security.JwtTokenProvider;
import com.backend.esignature.services.persistence.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticationControllerTest {
    @Mock
    private AuthenticationService authenticationService;
    @Mock
    private UserService userService;
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private org.springframework.security.authentication.AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationController authenticationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin_Success() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("password");
        AuthResponse authResponse = mock(AuthResponse.class);
        when(authenticationService.authenticate(loginRequest)).thenReturn(authResponse);

        ResponseEntity<AuthResponse> response = authenticationController.login(loginRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(authResponse, response.getBody());
    }

    @Test
    void testRegister_Success() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("testuser");
        AuthResponse authResponse = mock(AuthResponse.class);
        when(authResponse.getUserInfo()).thenReturn(new com.backend.esignature.dto.responses.UserInfo("testuser"));
        when(authenticationService.register(registerRequest)).thenReturn(authResponse);

        ResponseEntity<?> response = authenticationController.register(registerRequest);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("testuser"));
    }

    @Test
    void testRefreshToken_Success() {
        RefreshTokenRequest request = new RefreshTokenRequest();
        request.setRefreshToken("refresh-token");
        when(jwtTokenProvider.getUsernameFromToken("refresh-token")).thenReturn("testuser");
        when(authenticationService.refreshToken("refresh-token")).thenReturn(ResponseEntity.ok(mock(AuthResponse.class)));

        ResponseEntity<?> response = authenticationController.refreshToken(request);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Token refreshed successfully"));
    }
}
