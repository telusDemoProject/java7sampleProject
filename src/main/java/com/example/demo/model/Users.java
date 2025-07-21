
package com.example.demo.model;

import org.hibernate.validator.constraints.Email;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "USERS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {

    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    @Size(min = 2,max = 50, message = "Name must be between 2 and 50 characters")
    private String name;
    @Email
    @NotNull
    private String email;
}
