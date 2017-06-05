package com.chrisluttazi.holidaysrecipes.webcontrollers;

import com.chrisluttazi.holidaysrecipes.model.Ingredient;
import com.chrisluttazi.holidaysrecipes.model.persistance.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping("/manager/ingredient")
public class IngredientWebController {
    @Autowired
    IngredientService ingredientService;

    @RequestMapping(method = RequestMethod.GET)
    public String home(Model model) {
        model = addAttributes(model);
        model.addAttribute("ingredient", new Ingredient());
        return "ingredient/form";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String createIngredient(@Valid Ingredient ingredient, BindingResult bindingResult, Model model) {
        model = addAttributes(model);
        if (bindingResult.hasErrors()) {
            model.addAttribute("ingredient", ingredient);
            return "ingredient/form";
        }
        model.addAttribute("ingredient", ingredientService.create(ingredient));
        return "ingredient/result";
    }

    @RequestMapping(path = "{id}/update", method = RequestMethod.GET)
    public String update(@PathVariable("id") Long id, Model model) {
        model = addAttributes(model);
        model.addAttribute("ingredient", ingredientService.findByIngredientId(id));
        return "ingredient/form";
    }

    @RequestMapping(path = "{id}/update", method = RequestMethod.POST)
    public String updateUser(@PathVariable("id") Long id, @Valid Ingredient ingredient, BindingResult bindingResult,
                             Model model) {
        model = addAttributes(model);
        if (bindingResult.hasErrors()) {
            model.addAttribute("ingredient", ingredientService.findByIngredientId(id));
            return "ingredient/form";
        }
        ingredient.setIngredientId(id);
        model.addAttribute("ingredient", ingredientService.update(ingredient));
        return "ingredient/result";
    }

    @RequestMapping(path = "list", method = RequestMethod.GET)
    public String viewAll(Model model) {
        model = addAttributes(model);
        model.addAttribute("listIngredient", ingredientService.findAll());
        return "ingredient/list";
    }

    private Model addAttributes(Model model) {
        model.addAttribute("formTitle", "Ingredient Form");
        return model;
    }
}
