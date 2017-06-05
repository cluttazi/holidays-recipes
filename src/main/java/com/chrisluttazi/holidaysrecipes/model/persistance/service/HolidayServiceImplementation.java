package com.chrisluttazi.holidaysrecipes.model.persistance.service;

import com.chrisluttazi.holidaysrecipes.model.Holiday;
import com.chrisluttazi.holidaysrecipes.model.persistance.dao.HolidayDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HolidayServiceImplementation implements HolidayService {

    @Autowired
    private HolidayDAO holidayDAO;

    @Override
    public Holiday findByHolidayId(Long id) {
        return holidayDAO.findByHolidayId(id);
    }

    @Override
    public Holiday create(Holiday holiday) {
        if (holidayDAO.findByHolidayId(holiday.getHolidayId()) != null) {
            return null;
        }
        return holidayDAO.save(holiday);
    }

    @Override
    public Holiday update(Holiday holiday) {
        if (holidayDAO.findByHolidayId(holiday.getHolidayId()) != null) {
            return holidayDAO.save(holiday);
        }
        return null;
    }

    @Override
    public List<Holiday> findAll() {
        return holidayDAO.findAll();
    }

}
