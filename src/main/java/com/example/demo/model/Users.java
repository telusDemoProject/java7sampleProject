
package com.example.demo.model;

import org.hibernate.validator.constraints.Email;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

// If JPA/Hibernate compatibility is required, keep as a class. Otherwise, convert to a record:
// @Entity
// public record Users(Long id, String name, String email) {}
//
// If not possible, modernize as much as possible:
@Entity
@Table(name = "USERS")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;
    @Email
    @NotNull
    private String email;

    public Users() {}

    public Users(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
