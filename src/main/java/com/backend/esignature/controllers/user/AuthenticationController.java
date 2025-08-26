package com.backend.esignature.controllers.user;

import com.backend.esignature.config.security.JwtTokenProvider;
import com.backend.esignature.dto.requests.*;
import com.backend.esignature.dto.responses.LoginResponse;
import com.backend.esignature.entities.Users;
import com.backend.esignature.services.business.AuthenticationService;
import com.backend.esignature.services.persistence.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication management APIs")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    @Operation(
            summary = "User Login",
            description = "Authentication user and return JWT tokens",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Login successful"),
                    @ApiResponse(responseCode = "401", description = "Invalid credentials"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            }
    )
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest){
        try{
             log.info("Attempting login for user: {}", loginRequest.getUsername());

             AuthResponse authResponse = authenticationService.authenticate(loginRequest);
             
             return  ResponseEntity.ok(authResponse);
        }catch (Exception e){
            log.error("Authentication failed for user: {}", loginRequest.getUsername());
            throw new RuntimeException("Authentication failed for user: " + loginRequest.getUsername());
        }
    }
    @PostMapping("/register")
    @Operation(
            summary = "User registration",
            description = "Register a new user account",
            responses = {
                    @ApiResponse(responseCode = "201", description = "User registered successfully"),
                    @ApiResponse(responseCode = "400", description = "Registration failed"),
                    @ApiResponse(responseCode = "409", description = "Username or email already exists")
            }
    )
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        try {
            log.info("Attempting registration for user: {}", registerRequest.getUsername());

            AuthResponse authResponse = authenticationService.register(registerRequest);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("User registered successfully with username: " + authResponse.getUserInfo().getUsername());

        } catch (Exception e) {
            log.error("Registration failed for user: {}", registerRequest.getUsername(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Registration failed: " + e.getMessage());
        }
    }

    @PostMapping("/refresh")
    @Operation(
            summary = "Refresh access token",
            description = "Generate new access token using refresh token",
            responses = {
                @ApiResponse(responseCode = "200", description = "Token refreshed successfully"),
                @ApiResponse(responseCode = "400", description = "Invalid refresh token")
            }
    )
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        try {
            log.info("Attempting token refresh for user: {}", jwtTokenProvider.getUsernameFromToken(request.getRefreshToken()));
            AuthResponse response = authenticationService.refreshToken(request.getRefreshToken());
            return ResponseEntity.ok(response).body("Token refreshed successfully");
        
        } catch (Exception e) {
            log.error("Token refresh failed", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Token refresh failed: " + e.getMessage());
        }
    }

    @GetMapping("/me")
    @Operation(
            summary = "Get current user info",
            description = "Get information about the currently authenticated user",
            responses = {
                @ApiResponse(responseCode = "200", description = "User info retrieved successfully"),
                @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        try {
            String username = authentication.getName();
            Users user = userService.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            return ResponseEntity.ok(LoginResponse.builder()
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .fullName(user.getFullName())
                    .roles(user.getRoles().stream()
                            .map(role -> role.getName())
                            .toList())
                    .build());
        } catch (Exception e) {
            log.error("Failed to get current user info", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get user info: " + e.getMessage());
        }
    }

    @PostMapping("/logout")
    @Operation(
            summary = "User logout",
            description = "User logout and clear access token",
            responses = {
                @ApiResponse(responseCode = "200", description = "User logged out successfully"),
                @ApiResponse(responseCode = "400", description = "Invalid refresh token")
            }
    )
    public ResponseEntity<?> logout(@RequestBody RefreshTokenRequest request) {
        try {
            String refreshToken = request.getRefreshToken();
            authenticationService.logout(refreshToken);
            log.info("User logout successfully");
            return ResponseEntity.ok().build();
        }catch (Exception e){
            log.error("Authentication failed for user: {}", jwtTokenProvider.getUsernameFromToken(request.getRefreshToken()), e);
            throw new  RuntimeException("Authentication failed for user: " + jwtTokenProvider.getUsernameFromToken(request.getRefreshToken()));
        }
    }

    @PostMapping("/change-password")
    @Operation(
            summary = "User can change password",
            description = "User change password when user want to change to new password",
            responses = {
                @ApiResponse(responseCode = "200", description = "Password changed successfully"),
                @ApiResponse(responseCode = "400", description = "Invalid request")
            }
    )
    public ResponseEntity<?> changePassword(@RequestParam String username,@Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        try{
            authenticationService.changePassword(username,changePasswordRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body("Change password successfully");
        }catch (Exception e){
            log.error("Change password failed with user : {}", username);
            throw new RuntimeException("Change password failed with user: " + e.getMessage());
        }
    }

    @PostMapping("/forgot-password")
    @Operation(
            summary = "Forgot password",
            description = "User can change password when you forgot password",
            responses = {
                @ApiResponse(responseCode = "200", description = "Forgot password email sent"),
                @ApiResponse(responseCode = "400", description = "Invalid request")
            }
    )
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        try{
            authenticationService.forgotPassword(forgotPasswordRequest);
            return ResponseEntity.ok().body("Change new password successfully");
        } catch (RuntimeException e) {
            log.error("Forgot password failed", e);
            throw new RuntimeException(e);
        }

    }

    @PostMapping("/reset-password")
    @Operation(
        summary = "Reset password",
        description = "User can reset password using reset token",
        responses = {
            @ApiResponse(responseCode = "200", description = "Password reset successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
        }
    )
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest request){
        try{
            
            authenticationService.resetPassword(request);
            
            return ResponseEntity.ok().body("Reset password successfully");

        }catch( RuntimeException ex){
            log.error("Reset password failed", ex);
            throw new RuntimeException("Reset password failed: " + ex.getMessage());
        }
    }

}
