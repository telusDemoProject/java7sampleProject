package com.example.demo.repository;

import com.example.demo.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    
    @Query("SELECT u FROM Users u WHERE u.email LIKE %:domain")
    List<Users> findByEmailDomain(@Param("domain") String domain);
    
    @Query("SELECT u FROM Users u WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Users> findByNameContainingIgnoreCase(@Param("keyword") String keyword);
}
