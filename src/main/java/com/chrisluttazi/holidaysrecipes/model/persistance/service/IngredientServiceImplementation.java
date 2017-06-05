package com.chrisluttazi.holidaysrecipes.model.persistance.service;

import com.chrisluttazi.holidaysrecipes.model.Ingredient;
import com.chrisluttazi.holidaysrecipes.model.persistance.dao.IngredientDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientServiceImplementation implements IngredientService {

    @Autowired
    private IngredientDAO ingredientDAO;

    @Override
    public Ingredient findByIngredientId(Long id) {
        return ingredientDAO.findByIngredientId(id);
    }

    @Override
    public Ingredient create(Ingredient ingredient) {
        if (ingredientDAO.findByIngredientId(ingredient.getIngredientId()) != null) {
            return null;
        }
        return ingredientDAO.save(ingredient);
    }

    @Override
    public Ingredient update(Ingredient ingredient) {
        if (ingredientDAO.findByIngredientId(ingredient.getIngredientId()) != null) {
            return ingredientDAO.save(ingredient);
        }
        return null;
    }

    @Override
    public List<Ingredient> findAll() {
        return ingredientDAO.findAll();
    }
}
