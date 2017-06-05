package com.chrisluttazi.holidaysrecipes.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class SupplierMarket {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ingredient")
    private Ingredient ingredient;

    @Id
    @GeneratedValue
    private Long supplierMarketId;

    @NotNull
    @NotEmpty
    private String name;

    private boolean enabled;

    public SupplierMarket() {

    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Long getSupplierMarketId() {
        return supplierMarketId;
    }

    public void setSupplierMarketId(Long supplierMarketId) {
        this.supplierMarketId = supplierMarketId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "Wanna buy?<h3>" + ingredient + "</h3>" + "<p>look no further because " + name + " is selling</p>";
    }

}
