package com.chrisluttazi.holidaysrecipes.model.persistance.service;

import com.chrisluttazi.holidaysrecipes.model.CheffBook;
import com.chrisluttazi.holidaysrecipes.model.Instruction;
import com.chrisluttazi.holidaysrecipes.model.Recipe;
import com.chrisluttazi.holidaysrecipes.model.persistance.dao.InstructionDAO;
import com.chrisluttazi.holidaysrecipes.model.persistance.dao.RecipeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeServiceImplementation implements RecipeService {

    @Autowired
    private RecipeDAO recipeDAO;

    @Autowired
    private InstructionDAO instructionDAO;

    @Override
    public Recipe findByRecipeId(Long id) {
        return recipeDAO.findOne(id);
    }

    @Override
    public Recipe create(Recipe recipe) {
        if (recipeDAO.findByRecipeId(recipe.getRecipeId()) != null) {
            return null;
        }
        return recipeDAO.save(recipe);
    }

    @Override
    public Recipe update(Recipe recipe, Long id) {
        if (recipeDAO.findByRecipeId(recipe.getRecipeId()) == null) {
            return null;
        }
        Recipe oldRecipe = recipeDAO.findByRecipeId(id);

        // // Load a new recipe with info
        // Recipe newRecipe = new Recipe(recipe);
        // newRecipe.setPrevious(oldRecipe);
        // newRecipe.setPublished(true);
        //
        // // Save it
        // Recipe saved = recipeDAO.save(newRecipe);
        //
        // // update old recipe
        // oldRecipe.setPublished(false);
        // recipeDAO.save(oldRecipe);
        List<Instruction> listInstructions = oldRecipe.getInstruction();
        // I could have copied the instructions, but I would have repeated,
        // prefer to transfer

        recipe.setInstruction(new ArrayList<Instruction>());
        recipe.setRecipeId(null);
        // Load a new recipe with info
        Recipe newRecipe = recipeDAO.save(recipe);
        newRecipe.setPrevious(oldRecipe);
        newRecipe.setVersion(oldRecipe.getVersion() + 1.0F);
        newRecipe.setPublished(true);

        // Save it
        Recipe saved = recipeDAO.save(newRecipe);

        // update old recipe
        // oldRecipe.setInstruction(new ArrayList<Instruction>());

        for (Instruction i : listInstructions) {
            instructionDAO.save(new Instruction(i, saved));
        }

        for (Instruction i : listInstructions) {
            instructionDAO.delete(i.getInstructionId());
        }
        oldRecipe.setPublished(false);
        oldRecipe.setInstruction(new ArrayList<Instruction>());
        recipeDAO.save(oldRecipe);

        return saved;

    }

    @Override
    public List<Recipe> findAll() {
        List<Recipe> list = recipeDAO.findAll();
        List<Recipe> returnList = new ArrayList<Recipe>();
        for (Recipe recipe : list) {
            if (recipe.isPublished()) {
                returnList.add(recipe);
            }
        }
        return returnList;
    }

    @Override
    public List<Recipe> findByCheffBook(CheffBook cheffBook) {
        return recipeDAO.findByCheffBook(cheffBook);
    }
}
