package com.chrisluttazi.holidaysrecipes.model.persistance.service;

import com.chrisluttazi.holidaysrecipes.model.CheffBook;
import com.chrisluttazi.holidaysrecipes.model.Recipe;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface RecipeService {
    public Recipe findByRecipeId(Long id);

    @PreAuthorize("hasAuthority('CHEFF')")
    public Recipe create(Recipe recipe);

    public List<Recipe> findAll();

    public List<Recipe> findByCheffBook(CheffBook cheffBook);

    @PreAuthorize("hasAuthority('CHEFF')")
    public Recipe update(Recipe recipe, Long id);

}
