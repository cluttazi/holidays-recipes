package com.chrisluttazi.holidaysrecipes.model.persistance.dao;

import com.chrisluttazi.holidaysrecipes.model.CheffBook;
import com.chrisluttazi.holidaysrecipes.model.Recipe;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface RecipeDAO extends PagingAndSortingRepository<Recipe, Long> {
    @SuppressWarnings("unchecked")
    public Recipe save(Recipe recipe);

    public Recipe findByRecipeId(Long id);

    public List<Recipe> findAll();

    public List<Recipe> findByCheffBook(CheffBook cheffBook);

}
