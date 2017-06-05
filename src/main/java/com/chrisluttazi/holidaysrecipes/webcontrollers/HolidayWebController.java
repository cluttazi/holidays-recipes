package com.chrisluttazi.holidaysrecipes.webcontrollers;

import com.chrisluttazi.holidaysrecipes.model.Holiday;
import com.chrisluttazi.holidaysrecipes.model.persistance.service.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/manager/holiday")
public class HolidayWebController {
    @Autowired
    HolidayService holidayService;

    @RequestMapping(method = RequestMethod.GET)
    public String home(Model model) {
        model = addAttributes(model);
        model.addAttribute("holiday", new Holiday());
        return "holiday/form";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String createHoliday(@Valid Holiday holiday, BindingResult bindingResult, Model model) {
        model = addAttributes(model);
        if (bindingResult.hasErrors()) {
            model.addAttribute("holiday", holiday);
            return "holiday/form";
        }
        model.addAttribute("holiday", holidayService.create(holiday));
        return "holiday/result";
    }

    @RequestMapping(path = "{id}/update", method = RequestMethod.GET)
    public String update(@PathVariable("id") Long id, Model model) {
        model = addAttributes(model);
        model.addAttribute("holiday", holidayService.findByHolidayId(id));
        return "holiday/form";
    }

    @RequestMapping(path = "{id}/update", method = RequestMethod.POST)
    public String updateUser(@PathVariable("id") Long id, @Valid Holiday holiday, BindingResult bindingResult,
                             Model model) {
        model = addAttributes(model);
        if (bindingResult.hasErrors()) {
            model.addAttribute("holiday", holidayService.findByHolidayId(id));
            return "holiday/form";
        }
        holiday.setHolidayId(id);
        model.addAttribute("holiday", holidayService.update(holiday));
        return "holiday/result";
    }

    private Model addAttributes(Model model) {
        model.addAttribute("formTitle", "Holiday Form");
        return model;
    }

    @RequestMapping(path = "list", method = RequestMethod.GET)
    public String viewAll(Model model) {
        model.addAttribute("listHoliday", holidayService.findAll());
        return "holiday/list";
    }

    @RequestMapping(path = "search/{look}", method = RequestMethod.POST)
    public String search(@PathVariable("look") String look, Model model) {
        model = addAttributes(model);
        List<Holiday> list = new ArrayList<Holiday>();
        for (Holiday holiday : holidayService.findAll()) {
            list.add(holiday);
        }
        model.addAttribute("listHoliday", list);
        return "holiday/list";
    }
}
