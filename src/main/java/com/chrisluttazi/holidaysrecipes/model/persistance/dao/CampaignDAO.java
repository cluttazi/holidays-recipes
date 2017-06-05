package com.chrisluttazi.holidaysrecipes.model.persistance.dao;

import com.chrisluttazi.holidaysrecipes.model.Campaign;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;
import java.util.List;

//import org.springframework.data.repository.PagingAndSortingRepository;

//public interface CampaignDAO extends PagingAndSortingRepository<Campaign, Long> {
@Transactional
public interface CampaignDAO extends PagingAndSortingRepository<Campaign, Long> {
    @SuppressWarnings("unchecked")
    public Campaign save(Campaign campaign);

    public Campaign findByCampaignId(Long id);

    public List<Campaign> findAll();
}
