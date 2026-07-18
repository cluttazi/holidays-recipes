package com.chrisluttazi.holidaysrecipes.model.persistance.dao;

import com.chrisluttazi.holidaysrecipes.model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.transaction.Transactional;
import java.util.List;

//import org.springframework.data.jpa.repository.JpaRepository;

//public interface CampaignDAO extends JpaRepository<Campaign, Long> {
@Transactional
public interface CampaignDAO extends JpaRepository<Campaign, Long> {
    @SuppressWarnings("unchecked")
    public Campaign save(Campaign campaign);

    public Campaign findByCampaignId(Long id);

    public List<Campaign> findAll();
}
