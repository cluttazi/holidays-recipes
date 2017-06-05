package com.chrisluttazi.holidaysrecipes.model;

import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    private Long userId = 0L;

    @Id
    @Email
    @Column(name = "username", unique = true, nullable = false, length = 45)
    private String username;

    @Column(name = "password", nullable = false, length = 60)
    private String password;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Authority> authority = new HashSet<Authority>(0);

    private String name;

    public User() {
    }

    public User(String username, String password, boolean enabled) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }

    public User(String username, String password, boolean enabled, Set<Authority> authority, String name) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.authority = authority;
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Authority> getAuthority() {
        return this.authority;
    }

    public void setAuthority(Set<Authority> userAuthority) {
        this.authority = userAuthority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
