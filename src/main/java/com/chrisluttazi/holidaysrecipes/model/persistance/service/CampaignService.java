package com.chrisluttazi.holidaysrecipes.model.persistance.service;

import com.chrisluttazi.holidaysrecipes.model.Campaign;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface CampaignService {
    public Campaign findByCampaignId(Long id);

    @PreAuthorize("hasAuthority('ADVERTISER')")
    public Campaign create(Campaign campaign);

    public List<Campaign> findAll();

}
