package com.chrisluttazi.holidaysrecipes.webcontrollers;

import com.chrisluttazi.holidaysrecipes.auxiliary.jsonMarker;
import com.chrisluttazi.holidaysrecipes.model.Recipe;
import com.chrisluttazi.holidaysrecipes.model.User;
import com.chrisluttazi.holidaysrecipes.model.persistance.service.RecipeService;
import com.chrisluttazi.holidaysrecipes.model.persistance.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeWebController {
    @Autowired
    UserService userService;

    @Autowired
    RecipeService recipeService;

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        List<jsonMarker> jsonList = new ArrayList<jsonMarker>();
        for (Recipe recipe : recipeService.findAll()) {
            jsonList.add(new jsonMarker(recipe.getTitle(), recipe.getLatitude(), recipe.getLongitude(),
                    recipe.getHoliday().toString(), recipe.getRecipeId().toString()));
        }
        ObjectMapper mapper = new ObjectMapper();
        String listRecipeJSON = "";
        try {
            listRecipeJSON = mapper.writeValueAsString(jsonList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        if (userService.findAll().isEmpty()) {
            User admin = new User("admin@admin", "admin@admin", true);
            userService.createAdmin(admin);
        }

        model.addAttribute("listRecipeJSON", listRecipeJSON);
        return "index";
    }

    @RequestMapping(path = "login", method = RequestMethod.GET)
    public String login() {
        return "loginForm";
    }

    @RequestMapping(path = "signin.css", method = RequestMethod.GET)
    public String bug01() {
        return "index";
    }

    @RequestMapping(path = "register", method = RequestMethod.GET)
    public String register(Model model) {
        model.addAttribute("formTitle", "Register");
        model.addAttribute("user", new User());
        return "register";
    }

    // @RequestMapping(path = "error", method = RequestMethod.GET)
    // public String error() {
    // return "index";
    // }

    @RequestMapping(path = "register", method = RequestMethod.POST)
    public String createUser(@Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        model.addAttribute("user", userService.create(user));
        return "redirect: ";
    }
}
