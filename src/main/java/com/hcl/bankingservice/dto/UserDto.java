package com.hcl.bankingservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDto {

    private long   id;
    private String  username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String  password;
    private boolean active;
    private String  role;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}



