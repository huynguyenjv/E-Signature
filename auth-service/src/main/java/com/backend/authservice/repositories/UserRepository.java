package com.backend.authservice.repositories;

import com.backend.authservice.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, String> {
    Optional<Users> findByUsername(String username);

    Optional<Users> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    // Optimized query to fetch user with roles in one query
    @Query("SELECT u FROM users u LEFT JOIN FETCH u.roles WHERE u.username = :username")
    Optional<Users> findByUsernameWithRoles(@Param("username") String username);

    @Query("SELECT u FROM users u LEFT JOIN FETCH u.roles WHERE u.email = :email")
    Optional<Users> findByEmailWithRoles(@Param("email") String email);

    @Query("SELECT u FROM users u LEFT JOIN FETCH u.roles WHERE u.id = :id")
    Optional<Users> findByIdWithRoles(@Param("id") String id);

    // Find active users only
    @Query("SELECT u FROM users u WHERE u.isActive = true")
    Optional<Users> findByUsernameAndIsActiveTrue(String username);
}
