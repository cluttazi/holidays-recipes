package com.chrisluttazi.holidaysrecipes.model.persistance.service;

import com.chrisluttazi.holidaysrecipes.model.CheffBook;
import com.chrisluttazi.holidaysrecipes.model.User;
import com.chrisluttazi.holidaysrecipes.model.persistance.dao.CheffBookDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheffBookServiceImplementation implements CheffBookService {

    @Autowired
    private CheffBookDAO cheffBookDAO;

    @Override
    public List<CheffBook> findAll() {
        return cheffBookDAO.findAll();
    }

    private CheffBook setStatus(Long id, Boolean status) {
        CheffBook cheffBook = cheffBookDAO.findByCheffBookId(id);
        if (cheffBook == null) {
            return cheffBook;
        }
        cheffBook.setEnabled(status);
        return cheffBookDAO.save(cheffBook);
    }

    @Override
    public CheffBook findByCheffBookId(Long id) {
        return cheffBookDAO.findByCheffBookId(id);
    }

    @Override
    public CheffBook create(CheffBook cheffBook) {
        if (cheffBookDAO.findByCheffBookId(cheffBook.getCheffBookId()) != null) {
            return null;
        }
        return cheffBookDAO.save(cheffBook);
    }

    @Override
    public CheffBook update(CheffBook cheffBook, Long id, User user) {
        if (cheffBookDAO.findByCheffBookId(id) != null) {
            return cheffBookDAO.save(new CheffBook(cheffBook, id, user));
        }
        return null;
    }

    @Override
    public CheffBook enable(Long id) {
        return setStatus(id, true);
    }

    @Override
    public CheffBook disable(Long id) {
        return setStatus(id, false);
    }

    @Override
    public List<CheffBook> findByUser(User user) {
        return cheffBookDAO.findByUser(user);
    }
}
