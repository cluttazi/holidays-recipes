package com.chrisluttazi.holidaysrecipes.model;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Ingredient {

    @Id
    @GeneratedValue
    private Long ingredientId;

    @NotNull
    @NotEmpty
    @Length(min = 2, max = 24)
    private String title;

    @NotNull
    @NotEmpty
    @Length(min = 1, max = 200)
    private String description;

    public Ingredient() {
    }

    public Ingredient(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.title;
    }
}
