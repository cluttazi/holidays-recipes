package com.chrisluttazi.holidaysrecipes.webcontrollers;

import com.chrisluttazi.holidaysrecipes.model.Campaign;
import com.chrisluttazi.holidaysrecipes.model.persistance.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/campaign")
public class CampaignWebController {
    @Autowired
    CampaignService campaignService;

    @RequestMapping(method = RequestMethod.GET)
    public String home(Model model) {
        model = addAttributes(model);
        model.addAttribute("campaign", new Campaign());
        return "campaign/form";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String createCampaign(@Valid Campaign campaign, BindingResult bindingResult, Model model) {
        model = addAttributes(model);
        if (bindingResult.hasErrors()) {
            return "campaign/form";
        }
        // campaign.assignId();
        model.addAttribute("campaign", campaignService.create(campaign));
        return "campaign/result";
    }

    @RequestMapping(path = "list", method = RequestMethod.GET)
    public String viewCampaigns(Model model) {
        model = addAttributes(model);
        List<Campaign> list = campaignService.findAll();
        model.addAttribute("listCampaign", list);
        return "campaign/list";
    }

    private Model addAttributes(Model model) {
        model.addAttribute("formTitle", "Campaign Form");
        return model;
    }
}
