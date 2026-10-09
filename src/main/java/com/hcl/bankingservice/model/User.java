package com.hcl.bankingservice.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", nullable = false)
    private long  id;
    @Column(name = "user_name", nullable = false)
    private String  username;
    @Column(name = "password", nullable = false)
    private String  password;

    @Column(name = "active", nullable = false)
    private boolean active;
    @Column(name = "user_role", nullable = false)
    private String  role;

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }

    public String getRole() {
        return role;
    }

    public void setRole(final String role) {
        this.role = role;
    }
}



