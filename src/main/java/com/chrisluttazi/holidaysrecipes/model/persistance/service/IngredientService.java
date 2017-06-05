package com.chrisluttazi.holidaysrecipes.model.persistance.service;

import com.chrisluttazi.holidaysrecipes.model.Ingredient;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface IngredientService {
    public Ingredient findByIngredientId(Long id);

    @PreAuthorize("hasAuthority('MANAGER')")
    public Ingredient create(Ingredient ingredient);

    @PreAuthorize("hasAuthority('MANAGER')")
    public Ingredient update(Ingredient ingredient);

    public List<Ingredient> findAll();

}
