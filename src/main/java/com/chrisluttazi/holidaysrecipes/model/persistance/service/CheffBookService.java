package com.chrisluttazi.holidaysrecipes.model.persistance.service;

import com.chrisluttazi.holidaysrecipes.model.CheffBook;
import com.chrisluttazi.holidaysrecipes.model.User;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface CheffBookService {
    public CheffBook findByCheffBookId(Long id);

    @PreAuthorize("hasAuthority('CHEFF')")
    public CheffBook create(CheffBook cheffBook);

    @PreAuthorize("hasAuthority('CHEFF')")
    public CheffBook update(CheffBook cheffBook, Long id, User user);

    public List<CheffBook> findAll();

    public CheffBook enable(Long id);

    public CheffBook disable(Long id);

    public List<CheffBook> findByUser(User user);

}
