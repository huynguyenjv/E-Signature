package com.backend.userservice.repositories;

import com.backend.userservice.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Users, String> {

    List<Users> findByEmail(String email);

    void deleteByEmail(String email);
}
