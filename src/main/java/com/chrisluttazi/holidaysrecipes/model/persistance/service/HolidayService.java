package com.chrisluttazi.holidaysrecipes.model.persistance.service;

import com.chrisluttazi.holidaysrecipes.model.Holiday;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface HolidayService {
    public Holiday findByHolidayId(Long id);

    @PreAuthorize("hasAuthority('MANAGER')")
    public Holiday create(Holiday holiday);

    @PreAuthorize("hasAuthority('MANAGER')")
    public Holiday update(Holiday holiday);

    public List<Holiday> findAll();

}
