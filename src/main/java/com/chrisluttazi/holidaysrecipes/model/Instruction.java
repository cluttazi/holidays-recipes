package com.chrisluttazi.holidaysrecipes.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
public class Instruction {

    @Id
    @GeneratedValue
    private Long instructionId;

    @Min(1)
    @Max(24)
    private int position;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ingredient")
    @NotNull
    private Ingredient ingredient;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "recipe")
    private Recipe recipe;

    private float amount;


    @Length(min = 10, max = 140)
    private String description;

    public Instruction() {

    }

    public Instruction(int position, Ingredient ingredient, float amount, String description, Recipe recipe) {
        this.position = position;
        this.ingredient = ingredient;
        this.amount = amount;
        this.description = description;
        this.recipe = recipe;
    }

    public Instruction(Instruction i, Recipe recipe) {
        this.position = i.position;
        this.ingredient = i.ingredient;
        this.amount = i.amount;
        this.description = i.description;
        this.recipe = recipe;
    }

    public Long getInstructionId() {
        return instructionId;
    }

    public void setInstructionId(Long instructionId) {
        this.instructionId = instructionId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
