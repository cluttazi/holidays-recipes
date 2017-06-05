package com.chrisluttazi.holidaysrecipes.webcontrollers;

import com.chrisluttazi.holidaysrecipes.auxiliary.Utilities;
import com.chrisluttazi.holidaysrecipes.model.CheffBook;
import com.chrisluttazi.holidaysrecipes.model.persistance.service.CheffBookService;
import com.chrisluttazi.holidaysrecipes.model.persistance.service.UserService;
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
@RequestMapping("/cheff")
public class CheffBookWebController {
    @Autowired
    CheffBookService cheffBookService;

    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public String home(Model model) {
        model = addAttributes(model);
        model.addAttribute("cheffBook", new CheffBook());
        return "cheffbook/form";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String createCheffBook(@Valid CheffBook cheffBook, BindingResult bindingResult, Model model) {
        model = addAttributes(model);
        if (bindingResult.hasErrors()) {
            model.addAttribute("cheffBook", cheffBook);
            return "cheffbook/form";
        }
        cheffBook.setUser(userService.findByUsername(Utilities.currentUser()));
        model.addAttribute("cheffBook", cheffBookService.create(cheffBook));
        return "cheffbook/result";
    }

    @RequestMapping(path = "{id}/update", method = RequestMethod.GET)
    public String update(@PathVariable("id") Long id, Model model) {
        model = addAttributes(model);
        model.addAttribute("cheffBook", cheffBookService.findByCheffBookId(id));
        return "cheffbook/form";
    }

    @RequestMapping(path = "{id}/update", method = RequestMethod.POST)
    public String updateUser(@PathVariable("id") Long id, @Valid CheffBook cheffBook, BindingResult bindingResult,
                             Model model) {
        model = addAttributes(model);
        CheffBook old = cheffBookService.findByCheffBookId(id);
        model.addAttribute("cheffBook", old);
        if (bindingResult.hasErrors()) {
            return "cheffbook/form";
        }
        // cheffBook.setUser(userService.findByUsername(Utilities.currentUser()));
        model.addAttribute("cheffBook", cheffBookService.update(cheffBook, old.getCheffBookId(), old.getUser()));
        return "cheffbook/result";
    }

    @RequestMapping(path = "list", method = RequestMethod.GET)
    public String list(Model model) {
        model = addAttributes(model);
        List<CheffBook> list = cheffBookService.findByUser(userService.findByUsername(Utilities.currentUser()));
        model.addAttribute("listCheffBook", list);
        return "cheffbook/list";
    }

    private Model addAttributes(Model model) {
        model.addAttribute("formTitle", "Cheff Book Form");
        return model;
    }

}
