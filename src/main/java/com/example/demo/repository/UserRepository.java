package com.example.demo.repository;

import com.example.demo.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    List<Users> findByEmailContaining(String domain);
    List<Users> findByNameContainingIgnoreCase(String keyword);
    Optional<Users> findByEmail(String email);
}
