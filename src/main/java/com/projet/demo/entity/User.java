package com.projet.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder


@Entity
@Inheritance(strategy = InheritanceType.JOINED)

public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String username;

    private String email;

    private String phoneNumber;

    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private boolean enabled;
    private boolean firstLogin;


    @PrePersist
    public void init() {
        fullName = (firstName + " " + lastName).stripTrailing();
    }

}