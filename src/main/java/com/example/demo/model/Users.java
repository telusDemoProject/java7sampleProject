
package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Entity
public class Users {
    
    @Id
    @GeneratedValue
    private Long id;
    
    @NotNull
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;
    
    @Email
    @NotNull
    private String email;

    // Default constructor for JPA
    public Users() {
    }

    // Constructor for creating with all fields
    public Users(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    // Constructor for creating without ID (for new entities)
    public Users(String name, String email) {
        this(null, name, email);
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Modern Java 17 toString, equals, hashCode
    @Override
    public String toString() {
        return "Users[id=" + id + ", name='" + name + "', email='" + email + "']";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Users users = (Users) obj;
        return Objects.equals(id, users.id) && 
               Objects.equals(name, users.name) && 
               Objects.equals(email, users.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email);
    }
}
