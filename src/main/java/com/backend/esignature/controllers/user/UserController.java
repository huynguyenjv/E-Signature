package com.backend.esignature.controllers.user;

import com.backend.esignature.dto.requests.*;
import backend.esignature.dto.requests.UserRequest;
import backend.esignature.dto.responses.UserResponse;
import backend.esignature.dto.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Tag(name = "User", description = "User management APIs")
public class UserController {

    private final UserService userService;

    @GetMapping("/{username}")
    @Operation(
        summary = "Get user by username",
        description = "Retrieve user information by username",
        responses = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found")
        }
    )
    public ApiResponse<UserResponse> getUserByUsername(@PathVariable String username) {
        try{
            UserResponse userResponse = userService.findByUsername(username);
            return ApiResponse.<UserResponse>builder()
                    .code("200")
                    .message("User found")
                    .data(userResponse)
                    .build();
        }catch(Exception e){
            log.error("Error retrieving user by username {}: {}", username, e.getMessage());
            return ApiResponse.<UserResponse>builder()
                    .code("500")
                    .message("Error retrieving user")
                    .build();
        }
    }

    @PostMapping("/create")
    @Operation(
        summary = "Create a new user",
        description = "Create a new user with the provided information",
        responses = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid user data")
        }
    )
    public ApiResponse<UserResponse> createUser(@RequestBody UserRequest userRequest) {
        try {
            UserResponse userResponse = userService.createUser(userRequest);
            return ApiResponse.<UserResponse>builder()
                    .code("201")
                    .message("User created successfully")
                    .data(userResponse)
                    .build();
        } catch (Exception e) {
            log.error("Error creating user: {}", e.getMessage());
            return ApiResponse.<UserResponse>builder()
                    .code("400")
                    .message("Error creating user")
                    .build();
        }
    }

    @PostMapping("/assign-role")
    @Operation(
        summary = "Assign a role to a user",
        description = "Assign a specific role to a user by username",
        responses = {
            @ApiResponse(responseCode = "200", description = "Role assigned successfully"),
            @ApiResponse(responseCode = "404", description = "User or role not found")
        }
    )
    public ApiResponse<Void> assignRoleToUser(@RequestParam String userId, @RequestParam String roleName) {
        try {
            userService.assignRoleToUser(userId, roleName);
            return ApiResponse.<Void>builder()
                    .code("200")
                    .message("Role assigned successfully")
                    .build();
        } catch (Exception e) {
            log.error("Error assigning role {} to user {}: {}", roleName, userId, e.getMessage());
            return ApiResponse.<Void>builder()
                    .code("404")
                    .message("User or role not found")
                    .build();
        }
    }

    @PostMapping("/remove-role")
    @Operation(
        summary = "Remove a role from a user",
        description = "Remove a specific role from a user by username",
        responses = {
            @ApiResponse(responseCode = "200", description = "Role removed successfully"),
            @ApiResponse(responseCode = "404", description = "User or role not found")
        }
    )
    public ApiResponse<Void> removeRoleFromUser(@RequestParam String username, @RequestParam String roleName) {
        try {
            userService.removeRoleFromUser(username, roleName);
            return ApiResponse.<Void>builder()
                    .code("200")
                    .message("Role removed successfully")
                    .build();
        } catch (Exception e) {
            log.error("Error removing role {} from user {}: {}", roleName, username, e.getMessage());
            return ApiResponse.<Void>builder()
                    .code("404")
                    .message("User or role not found")
                    .build();
        }
    }


    @GetMapping("/{email}")
    @Operation(
        summary = "Find user by email",
        description = "Retrieve user information by email"
    )
    public ApiResponse<UserResponse> getUserByEmail(@PathVariable String email) {
        try{
            UserResponse userResponse = userService.findByEmail(email);
            return ApiResponse.<UserResponse>builder()
                    .code("200")
                    .message("User retrieved successfully")
                    .data(userResponse)
                    .build();
        }catch(Exception e){
            log.error("Error retrieving user by email {}: {}", email, e.getMessage());
            return ApiResponse.<UserResponse>builder()
                    .code("500")
                    .message("Error retrieving user by email: " + email)
                    .build();
        }
    }


    @GetMapping("/{id}")
    @Operation(
        summary = "Find user by id",
        description = "Retrieve user information by id"
    )
    public ApiResponse<UserResponse> getUserById(@PathVariable String id) {
        UserResponse userResponse = userService.findById(id);
            return ApiResponse.<UserResponse>builder()
                    .code("200")
                    .message("User retrieved successfully")
                    .data(userResponse)
                    .build();
    
    }



}
