package com.chrisluttazi.holidaysrecipes.webcontrollers;

import com.chrisluttazi.holidaysrecipes.model.Instruction;
import com.chrisluttazi.holidaysrecipes.model.Recipe;
import com.chrisluttazi.holidaysrecipes.model.persistance.service.IngredientService;
import com.chrisluttazi.holidaysrecipes.model.persistance.service.InstructionService;
import com.chrisluttazi.holidaysrecipes.model.persistance.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/cheff/instruction")
public class InstructionWebController {
    @Autowired
    InstructionService instructionService;

    @Autowired
    IngredientService ingredientService;

    @Autowired
    RecipeService recipeService;

    @RequestMapping(method = RequestMethod.GET)
    public String home(Model model) {
        model = addAttributes(model);
        model.addAttribute("instruction", new Instruction());
        return "instruction/form";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String createRecipe(@Valid Instruction instruction, BindingResult bindingResult, Model model) {
        model = addAttributes(model);
        if (bindingResult.hasErrors()) {
            return "instruction/form";
        }
        model.addAttribute("recipe", instructionService.create(instruction));
        return "instruction/result";
    }

    @RequestMapping(path = "{id}/update", method = RequestMethod.GET)
    public String update(@PathVariable("id") Long id, Model model) {
        model = addAttributes(model);
        model.addAttribute("recipe", instructionService.findByInstructionId(id));
        return "instruction/form";
    }

    @RequestMapping(path = "{id}/update", method = RequestMethod.POST)
    public String updateUser(@PathVariable("id") Long id, @Valid Instruction instruction, BindingResult bindingResult,
                             Model model) {
        model = addAttributes(model);
        if (bindingResult.hasErrors()) {
            return "instruction/form";
        }
        model.addAttribute("recipe", instructionService.update(instruction));
        return "instruction/result";
    }

    @RequestMapping(path = "{id}/list", method = RequestMethod.GET)
    public String list(@PathVariable("id") Long id, Model model) {
        model = addAttributes(model);
        Recipe recipe = recipeService.findByRecipeId(id);
        List<Instruction> list = recipe.getInstruction();
        model.addAttribute("listInstruction", list);
        return "instruction/list";
    }

    private Model addAttributes(Model model) {
        model.addAttribute("formTitle", "Instruction Form");
        model.addAttribute("listIngredients", ingredientService.findAll());
        model.addAttribute("listRecipe", recipeService.findAll());
        return model;
    }
}
