package com.openclassrooms.ycyw.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "users")
public class Auth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Email
    @Column(unique = true)
    private String email;

    @NotNull
    @Column(updatable = false, name = "first_name")
    private String firstName;

    @NotNull
    @Column(updatable = false, name = "last_name")
    private String lastName;

    @NotNull
    private String password;

    @Column(updatable = false, name = "created_at")
    private LocalDate createdAt;

    @Column(updatable = true, name = "updated_at")
    private LocalDate updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDate.now();
        updatedAt = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDate.now();
    }

    public Auth() {}
}
