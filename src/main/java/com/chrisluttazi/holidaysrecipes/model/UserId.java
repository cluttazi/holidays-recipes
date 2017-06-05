package com.chrisluttazi.holidaysrecipes.model;

import javax.persistence.*;

@Entity
public class UserId {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "username")
    private User user;

    public UserId() {
    }

    public UserId(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
