package com.stream.authentication.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user_roles", schema = "authentication_service")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

    public UserRole(Long id) {
        this.id = id;
    }

    public UserRole() {

    }
}