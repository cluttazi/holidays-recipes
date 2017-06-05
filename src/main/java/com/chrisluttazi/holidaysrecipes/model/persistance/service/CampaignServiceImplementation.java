package com.chrisluttazi.holidaysrecipes.model.persistance.service;

import com.chrisluttazi.holidaysrecipes.model.Campaign;
import com.chrisluttazi.holidaysrecipes.model.persistance.dao.CampaignDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CampaignServiceImplementation implements CampaignService {

    @Autowired
    private CampaignDAO campaignDAO;

    @Override
    public Campaign findByCampaignId(Long id) {
        return campaignDAO.findByCampaignId(id);
    }

    @Override
    public Campaign create(Campaign campaign) {
        if (campaignDAO.findByCampaignId(campaign.getCampaignId()) != null) {
            return null;
        }
        return campaignDAO.save(campaign);
    }

    @Override
    public List<Campaign> findAll() {
        return (List<Campaign>) campaignDAO.findAll();
    }

}
