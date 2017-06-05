package com.chrisluttazi.holidaysrecipes.webcontrollers;

import com.chrisluttazi.holidaysrecipes.auxiliary.Utilities;
import com.chrisluttazi.holidaysrecipes.model.*;
import com.chrisluttazi.holidaysrecipes.model.persistance.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/cheff/recipe")
public class RecipeWebController {
    @Autowired
    RecipeService recipeService;

    @Autowired
    CheffBookService cheffBookService;

    @Autowired
    HolidayService holidayService;

    @Autowired
    UserService userService;

    @Autowired
    CampaignService campaignService;

    @Autowired
    SupplierMarketService supplierService;

    @RequestMapping(method = RequestMethod.GET)
    public String home(Model model) {
        model = addAttributes(model);
        model.addAttribute("recipe", new Recipe());
        return "recipe/form";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String createRecipe(@Valid Recipe recipe, BindingResult bindingResult, Model model) {
        model = addAttributes(model);
        if (bindingResult.hasErrors()) {
            return "recipe/form";
        }
        model.addAttribute("recipe", recipeService.create(recipe));
        return "recipe/result";
    }

    @RequestMapping(path = "{id}/view", method = RequestMethod.GET)
    public String viewRecipe(@PathVariable("id") Long id, Model model) {
        model = addAttributes(model);
        model.addAttribute("commentsEnabled", true);
        Recipe recipe = recipeService.findByRecipeId(id);
        model.addAttribute("formTitle", "Recipe Viewer");
        model.addAttribute("recipe", recipe);
        return "recipe/result";
    }

    @RequestMapping(path = "{id}/update", method = RequestMethod.GET)
    public String update(@PathVariable("id") Long id, Model model) {
        model = addAttributes(model);
        model.addAttribute("recipe", recipeService.findByRecipeId(id));
        return "recipe/form";
    }

    @RequestMapping(path = "{id}/update", method = RequestMethod.POST)
    public String updateUser(@PathVariable("id") Long id, @Valid Recipe recipe, BindingResult bindingResult,
                             Model model) {
        model = addAttributes(model);
        model.addAttribute("recipe", recipeService.findByRecipeId(id));
        if (bindingResult.hasErrors()) {
            return "recipe/form";
        }
        recipe.setRecipeId(id);
        model.addAttribute("recipe", recipeService.update(recipe, id));
        return "recipe/result";
    }

    @RequestMapping(path = "{id}/list", method = RequestMethod.GET)
    public String viewAll(@PathVariable("id") Long id, Model model) {
        model = addAttributes(model);
        CheffBook cheffBook = cheffBookService.findByCheffBookId(id);
        List<Recipe> list = cheffBook.getRecipe();
        model.addAttribute("listRecipe", list);
        return "recipe/list";
    }

    @RequestMapping(value = "{id}/photo", method = RequestMethod.GET)
    public @ResponseBody
    String viewPhoto(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Recipe recipe = recipeService.findByRecipeId(id);
        byte[] photoBytes = recipe.getPhotoBytes();
        if (photoBytes != null) {
            int photoLength = photoBytes.length;
            try (ServletOutputStream sos = response.getOutputStream()) {
                response.setContentType(recipe.getPhotoContentType());
                response.setContentLength(photoLength);
                response.setHeader("Content-Disposition", "inline; filename=\"" + recipe.getPhotoFilename() + "\"");

                sos.write(photoBytes);
                sos.flush();
            }
        }
        return "";
    }

    @RequestMapping(path = "list", method = RequestMethod.GET)
    public String viewMyRecipes(Model model) {
        model = addAttributes(model);
        List<CheffBook> books = cheffBookService.findByUser(userService.findByUsername(Utilities.currentUser()));
        List<Recipe> list = new ArrayList<Recipe>();
        for (CheffBook cheffBook : books) {
            for (Recipe recipe : cheffBook.getRecipe()) {
                if (recipe.getCheffBook().getUser().getUsername().equalsIgnoreCase(Utilities.currentUser())) {
                    list.add(recipe);
                }
            }
        }
        model.addAttribute("listRecipe", list);
        return "recipe/list";
    }

    private Model addAttributes(Model model) {
        model.addAttribute("formTitle", "Recipe Form");
        String username = Utilities.currentUser();
        if (!username.isEmpty()) {
            User user = userService.findByUsername(username);
            List<CheffBook> list = cheffBookService.findByUser(user);
            model.addAttribute("listCheffBook", list);
        }
        model.addAttribute("listHoliday", holidayService.findAll());
        model.addAttribute("newCampaign", randomCampaign());
        model.addAttribute("newSupplierMarket", randomMarket());
        model.addAttribute("upcomming", upcommingHolidays());
        return model;
    }

    private String randomCampaign() {
        List<Campaign> list = campaignService.findAll();
        Random randomGenerator = new Random();
        int index = randomGenerator.nextInt(list.size());

        return list.get(index).toString();
    }

    private String randomMarket() {
        List<SupplierMarket> list = supplierService.findAll();
        Random randomGenerator = new Random();
        int index = randomGenerator.nextInt(list.size());

        return list.get(index).toString();
    }

    private String upcommingHolidays() {
        Date todayDate = new Date();

        List<Holiday> list = holidayService.findAll();
        String returnString = "";

        for (Holiday holiday : list) {
            if (holiday.getDate().after(todayDate)) {
                returnString += "<p>" + holiday.getTitle() + "</p>";
            }
        }

        return returnString;

    }
}
