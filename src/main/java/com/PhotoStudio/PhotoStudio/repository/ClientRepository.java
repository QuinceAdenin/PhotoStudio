package com.PhotoStudio.PhotoStudio.repository;

import com.PhotoStudio.PhotoStudio.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findByFullNameContainingIgnoreCase(String name);
}