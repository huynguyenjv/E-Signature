package com.backend.userservice.repositories;

import com.backend.userservice.entities.Contacts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactsRepository extends JpaRepository<Contacts, String> {
}
