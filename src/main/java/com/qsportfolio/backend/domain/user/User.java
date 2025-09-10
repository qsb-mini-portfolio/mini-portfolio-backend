package com.qsportfolio.backend.domain.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    UUID id;
    @Column(nullable = false, unique = true)
    String username;
    @Column(nullable = false)
    String password;
}
