package com.chrisluttazi.holidaysrecipes.model.persistance.dao;

import com.chrisluttazi.holidaysrecipes.model.CheffBook;
import com.chrisluttazi.holidaysrecipes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.transaction.Transactional;
import java.util.List;

@Transactional
public interface CheffBookDAO extends JpaRepository<CheffBook, Long> {
    @SuppressWarnings("unchecked")
    public CheffBook save(CheffBook campaign);

    public CheffBook findByCheffBookId(Long id);

    public List<CheffBook> findAll();

    public List<CheffBook> findByUser(User user);

}
