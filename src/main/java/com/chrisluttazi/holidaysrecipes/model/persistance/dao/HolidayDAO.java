package com.chrisluttazi.holidaysrecipes.model.persistance.dao;

import com.chrisluttazi.holidaysrecipes.model.Holiday;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface HolidayDAO extends PagingAndSortingRepository<Holiday, Long> {
    @SuppressWarnings("unchecked")
    public Holiday save(Holiday holiday);

    public Holiday findByHolidayId(Long id);

    public List<Holiday> findAll();

}
