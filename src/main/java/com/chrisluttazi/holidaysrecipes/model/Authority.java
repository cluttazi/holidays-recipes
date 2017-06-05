package com.chrisluttazi.holidaysrecipes.model;

import javax.persistence.*;

@Entity
@Table(name = "authorities", uniqueConstraints = @UniqueConstraint(columnNames = {"authority", "username"}))
public class Authority {

    @Id
    @GeneratedValue
    @Column(name = "authority_id", unique = true, nullable = false)
    private Integer authorityId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username", nullable = false)
    private User user;
    @Column(name = "authority", nullable = false, length = 45)
    private String authority;

    public Authority() {
    }

    public Authority(User user, String role) {
        this.user = user;
        this.authority = role;
    }

    public Integer getUserRoleId() {
        return this.authorityId;
    }

    public void setUserRoleId(Integer authorityId) {
        this.authorityId = authorityId;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAuthority() {
        return this.authority;
    }

    public void setAuthority(String role) {
        this.authority = role;
    }

    @Override
    public String toString() {
        return authority;
    }
}
