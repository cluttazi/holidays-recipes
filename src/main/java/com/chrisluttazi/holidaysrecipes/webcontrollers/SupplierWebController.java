package com.chrisluttazi.holidaysrecipes.webcontrollers;

import com.chrisluttazi.holidaysrecipes.model.SupplierMarket;
import com.chrisluttazi.holidaysrecipes.model.persistance.service.IngredientService;
import com.chrisluttazi.holidaysrecipes.model.persistance.service.RecipeService;
import com.chrisluttazi.holidaysrecipes.model.persistance.service.SupplierMarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/supplier")
public class SupplierWebController {
    @Autowired
    SupplierMarketService supplierService;

    @Autowired
    IngredientService ingredientService;

    @Autowired
    RecipeService recipeService;

    @RequestMapping(method = RequestMethod.GET)
    public String home(Model model) {
        model = addAttributes(model);
        model.addAttribute("supplier", new SupplierMarket());
        return "supplier/form";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String createRecipe(@Valid SupplierMarket supplier, BindingResult bindingResult, Model model) {
        model = addAttributes(model);
        if (bindingResult.hasErrors()) {
            return "supplier/form";
        }
        model.addAttribute("supplier", supplierService.create(supplier));
        return "supplier/result";
    }

    @RequestMapping(path = "list", method = RequestMethod.GET)
    public String list(Model model) {
        model = addAttributes(model);
        List<SupplierMarket> list = supplierService.findAll();
        model.addAttribute("listSupplier", list);
        return "supplier/list";
    }

    private Model addAttributes(Model model) {
        model.addAttribute("formTitle", "Supplier Form");
        model.addAttribute("listIngredients", ingredientService.findAll());
        return model;
    }
}
