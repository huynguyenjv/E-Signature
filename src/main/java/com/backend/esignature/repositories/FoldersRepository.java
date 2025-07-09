package com.backend.esignature.repositories;

import com.backend.esignature.entities.Folders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoldersRepository extends JpaRepository<Folders, String> {
}
