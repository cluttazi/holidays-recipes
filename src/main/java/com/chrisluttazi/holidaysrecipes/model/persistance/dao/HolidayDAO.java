package com.chrisluttazi.holidaysrecipes.model.persistance.dao;

import com.chrisluttazi.holidaysrecipes.model.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.transaction.Transactional;
import java.util.List;

@Transactional
public interface HolidayDAO extends JpaRepository<Holiday, Long> {
    @SuppressWarnings("unchecked")
    public Holiday save(Holiday holiday);

    public Holiday findByHolidayId(Long id);

    public List<Holiday> findAll();

}
