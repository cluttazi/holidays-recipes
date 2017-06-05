package com.chrisluttazi.holidaysrecipes.webcontrollers;

import com.chrisluttazi.holidaysrecipes.auxiliary.Utilities;
import com.chrisluttazi.holidaysrecipes.model.Authority;
import com.chrisluttazi.holidaysrecipes.model.Role;
import com.chrisluttazi.holidaysrecipes.model.User;
import com.chrisluttazi.holidaysrecipes.model.persistance.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserWebController {
    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public String newUser(Model model) {
        model = addAttributes(model);
        model.addAttribute("user", new User());
        return "user/form";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String createUser(@Valid User user, BindingResult bindingResult, Model model) {
        model = addAttributes(model);
        if (bindingResult.hasErrors()) {
            return "user/form";
        }
        model.addAttribute("user", userService.create(user));
        return "user/result";
    }

    @RequestMapping(path = "register", method = RequestMethod.POST)
    public String registerUser(@Valid User user, BindingResult bindingResult, Model model) {
        model = addAttributes(model);
        if (bindingResult.hasErrors()) {
            return "user/form";
        }
        model.addAttribute("user", userService.create(user));
        return "user/result";
    }

    @RequestMapping(path = "register", method = RequestMethod.GET)
    public String newRegisterUser(Model model) {
        model = addAttributes(model);
        model.addAttribute("user", new User());
        return "user/form";
    }

    @RequestMapping(path = "changePassword", method = RequestMethod.GET)
    public String changePassword(Model model) {
        model = addAttributes(model);
        User user = userService.findByUsername(Utilities.currentUser());
        model.addAttribute("user", user);
        String url = "redirect:" + user.getUserId().toString().trim() + "/update";
        return url;
    }

    @RequestMapping(path = "{id}/{role}/add", method = RequestMethod.GET)
    public String addRole(@PathVariable("id") Long id, @PathVariable("role") String role, Model model) {
        User user = userService.findByUserId(id);
        boolean add = false;
        try {
            Role[] values = Role.values();
            for (Role value : values) {
                String s = value.toString();
                if (s.equalsIgnoreCase(role)) {
                    add = true;
                }
            }

        } catch (Exception e) {
            model = addAttributes(model);
            return "user/list";
        }
        for (Authority auth : user.getAuthority()) {
            if (auth.getAuthority().equalsIgnoreCase(role)) {
                add = false;
            }
        }
        if (add) {
            userService.addAuthority(id, role);
        }
        model = addAttributes(model);
        return "user/list";
    }

    @RequestMapping(path = "{id}/removeRole", method = RequestMethod.GET)
    public String removeRoles(@PathVariable("id") Long id, Model model) {
        model = addAttributes(model);
        userService.removeAuthority(id);
        return "user/list";
    }

    @RequestMapping(path = "{id}/update", method = RequestMethod.GET)
    public String update(@PathVariable("id") Long id, Model model) {
        model = addAttributes(model);
        User user = userService.findByUserId(id);
        model.addAttribute("user", user);
        return "user/form";
    }

    @RequestMapping(path = "{id}/update", method = RequestMethod.POST)
    public String updateUser(@PathVariable("id") Long id, @Valid User user, BindingResult bindingResult, Model model) {
        model = addAttributes(model);
        User userError = userService.findByUserId(id);
        userError.setPassword("");
        model.addAttribute("user", userError);
        if (bindingResult.hasErrors()) {
            return "user/form";
        }
        model.addAttribute("user", userService.update(user));
        return "user/result";
    }

    @RequestMapping(path = "list", method = RequestMethod.GET)
    public String viewAll(Model model) {
        model = addAttributes(model);
        return "user/list";
    }

    @RequestMapping(path = "{id}/true", method = RequestMethod.GET)
    public String enable(@PathVariable("id") Long id, Model model) {
        userService.enable(id);
        model = addAttributes(model);
        return "user/list";
    }

    @RequestMapping(path = "{id}/false", method = RequestMethod.GET)
    public String disable(@PathVariable("id") Long id, Model model) {
        userService.disable(id);
        model = addAttributes(model);
        return "user/list";
    }

    private Model addAttributes(Model model) {
        model.addAttribute("formTitle", "User Form");
        model.addAttribute("listUser", userService.findAll());
        return model;
    }
}
