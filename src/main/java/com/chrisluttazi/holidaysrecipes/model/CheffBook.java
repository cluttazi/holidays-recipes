package com.chrisluttazi.holidaysrecipes.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class CheffBook {

    @Id
    @GeneratedValue
    private Long cheffBookId;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "cheffBook")
    @NotNull
    private List<Recipe> recipe = new ArrayList<Recipe>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "username")
    private User user;

    @NotEmpty
    private String title;

    @NotNull
    private Cuisine cuisine;

    private boolean enabled = true;

    public CheffBook() {
        this.cuisine = Cuisine.ASIAN;

    }

    public CheffBook(List<Recipe> recipe, User user, String title, Cuisine cuisine, boolean enabled) {
        this.recipe = recipe;
        this.user = user;
        this.title = title;
        this.cuisine = cuisine;
        this.enabled = enabled;
    }

    public CheffBook(User currentUser) {
        this.user = currentUser;
    }

    public CheffBook(CheffBook cheffBook, Long id, User user) {
        this.recipe = cheffBook.getRecipe();
        this.user = cheffBook.getUser();
        this.title = cheffBook.getTitle();
        this.cuisine = cheffBook.getCuisine();
        this.enabled = cheffBook.isEnabled();
        this.cheffBookId = cheffBook.getCheffBookId();
        this.user = user;
        this.cheffBookId = id;
    }

    public Long getCheffBookId() {
        return cheffBookId;
    }

    public void setCheffBookId(Long cheffBookId) {
        this.cheffBookId = cheffBookId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Recipe> getRecipe() {
        return recipe;
    }

    public void setRecipe(List<Recipe> recipe) {
        this.recipe = recipe;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Cuisine getCuisine() {
        return cuisine;
    }

    public void setCuisine(Cuisine cuisine) {
        this.cuisine = cuisine;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return title;
    }

}
