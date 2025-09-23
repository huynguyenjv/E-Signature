package com.backend.authservice.services;


import com.backend.authservice.config.security.JwtTokenProvider;
import com.backend.authservice.dto.request.*;
import com.backend.authservice.dto.response.AuthResponse;
import com.backend.authservice.dto.response.UserResponse;
import com.backend.authservice.entities.PasswordResetToken;
import com.backend.authservice.entities.RefreshToken;
import com.backend.authservice.entities.Role;
import com.backend.authservice.entities.Users;
import com.backend.authservice.kafka.UserEventProducer;
import com.backend.authservice.mapper.UserMapper;
import com.backend.authservice.repositories.PasswordResetTokenRepository;
import com.backend.authservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserEventProducer userEventProducer;
    private final RoleService roleService;
    private final EmailService emailService;
    private final UserService userService;
    private final UserMapper userMapper;

    @Transactional
    public AuthResponse authenticate(LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Users user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow( () -> new UsernameNotFoundException( "User not found : "+ loginRequest.getUsername()));

        user.setLastLogin(new Timestamp(System.currentTimeMillis()));
        userRepository.save(user);

        String accessToken = jwtTokenProvider.generateAccessToken(authentication);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        userEventProducer.publishUserLogin(user);
        return buildAuthResponse(user, accessToken, refreshToken.toString());
    }

    @Transactional
    public AuthResponse register(RegisterRequest registerRequest){
        if(userRepository.findByUsername(registerRequest.getUsername()).isPresent()){
            throw new RuntimeException("Username already exists");
        }
        if(userRepository.existsByEmail(registerRequest.getEmail())){
            throw new RuntimeException("Email already exists");
        }

        Users user = Users.builder()
                .username(registerRequest.getUsername())
                .passwordHash(registerRequest.getPassword())
                .email(registerRequest.getEmail())
                .fullName(registerRequest.getFullName())
                .isActive(true)
                .build();

        Role userRole = roleService.getOrCreateDefaultUserRole();
        user.addRole(userRole);

        user = userRepository.save(user);

        String accessToken = jwtTokenProvider.generateAccessToken(user.getUsername());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        userEventProducer.publishUserRegister(user);

        log.info("New user registered: {}", user.getUsername());

        return buildAuthResponse(user, accessToken, refreshToken.toString());
    }

    @Transactional
    public void logout(String refreshToken){
        refreshTokenService.revokeToken(refreshToken);
        SecurityContextHolder.clearContext();
    }

    @Transactional
    public void  logoutALl(String username){
        Users user = userRepository.findByUsername(username)
                .orElseThrow( () -> new UsernameNotFoundException("User not found : "+ username));

        refreshTokenService.revokeAllUserTokens(user.getId());
        SecurityContextHolder.clearContext();
    }

    public boolean validateToken(String token){
        return jwtTokenProvider.validateToken(token);
    }

    @Transactional
    public void changePassword(String username, ChangePasswordRequest changePasswordRequest){
        Users user = userRepository.findByUsername(username)
                .orElseThrow( () -> new UsernameNotFoundException("User not found : "+ username));

        if(!passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), user.getPasswordHash())){
            throw new RuntimeException("Current password does not match old password");
        }

        user.setPasswordHash(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        userRepository.save(user);

        refreshTokenService.revokeAllUserTokens(user.getId());

        log.info("Change password for user: {}", user.getUsername());
    }

    @Transactional
    public void forgotPassword(ForgotPasswordRequest forgotPasswordRequest){
        Users user = userRepository.findByEmail(forgotPasswordRequest.getEmail())
                .orElseThrow( () ->  new UsernameNotFoundException("User not found : "+ forgotPasswordRequest.getEmail()));

        PasswordResetToken passwordResetToken = PasswordResetToken.builder()
                .token(UUID.randomUUID().toString())
                .user(user)
                .expiresAt(LocalDateTime.now().plusHours(1))
                .isUsed(false)
                .build();

        passwordResetTokenRepository.save(passwordResetToken);

        emailService.sendPasswordResetEmail(user.getEmail(),passwordResetToken.getToken());

        log.info("Password reset token sent to email: {}", forgotPasswordRequest.getEmail());
    }

    @Transactional
    public void resetPassword(ResetPasswordRequest resetPasswordRequest){
        PasswordResetToken resetToken = passwordResetTokenRepository.findByTokenAndIsUsedFalse(resetPasswordRequest.getToken())
                .orElseThrow(() -> new RuntimeException("Invalid reset token"));

        if (resetToken.isExpired()) {
            throw new RuntimeException("Reset token has expired");
        }

        Users user = resetToken.getUser();
        user.setPasswordHash(passwordEncoder.encode(resetPasswordRequest.getNewPassword()));
        userRepository.save(user);

        // Mark token as used
        resetToken.setUsed(true);
        passwordResetTokenRepository.save(resetToken);

        // Revoke all refresh tokens
        refreshTokenService.revokeAllUserTokens(user.getId());

        log.info("Password reset completed for user: {}", user.getUsername());
    }

    @Transactional
    public ResponseEntity<AuthResponse> refreshToken(String refreshToken) {
        try{
            if(jwtTokenProvider.validateToken(refreshToken)){
                String username = jwtTokenProvider.getUsernameFromToken(refreshToken);
                String newAccessToken = jwtTokenProvider.generateAccessToken(username);

                UserResponse user = userService.findByUsername(username);

                AuthResponse response = buildAuthResponse(userMapper.toEntity(user), newAccessToken, refreshToken);
                return ResponseEntity.ok(response);
            }else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

        }catch (Exception e){
            log.error("Refresh token failed: {}", refreshToken, e);
            throw new RuntimeException("Refresh token failed: " + e.getMessage());
        }
    }

    private AuthResponse buildAuthResponse(Users user, String accessToken, String refreshToken) {
        AuthResponse response = new AuthResponse();
        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);
        response.setExpiresIn(900L); // 15 minutes in seconds

        AuthResponse.UserInfo userInfo = new AuthResponse.UserInfo();
        userInfo.setId(user.getId());
        userInfo.setUsername(user.getUsername());
        userInfo.setEmail(user.getEmail());
        userInfo.setFullName(user.getFullName());
        userInfo.setRoles(user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList()));
        userInfo.setActive(user.isActive());

        response.setUserInfo(userInfo);
        return response;
    }
}
