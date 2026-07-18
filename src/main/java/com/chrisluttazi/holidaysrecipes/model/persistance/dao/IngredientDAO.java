package com.chrisluttazi.holidaysrecipes.model.persistance.dao;

import com.chrisluttazi.holidaysrecipes.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.transaction.Transactional;
import java.util.List;

@Transactional
public interface IngredientDAO extends JpaRepository<Ingredient, Long> {
    @SuppressWarnings("unchecked")
    public Ingredient save(Ingredient ingredient);

    public Ingredient findByIngredientId(Long id);

    public List<Ingredient> findAll();

}
