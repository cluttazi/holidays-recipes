package com.chrisluttazi.holidaysrecipes.model.persistance.dao;

import com.chrisluttazi.holidaysrecipes.model.CheffBook;
import com.chrisluttazi.holidaysrecipes.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface CheffBookDAO extends PagingAndSortingRepository<CheffBook, Long> {
    @SuppressWarnings("unchecked")
    public CheffBook save(CheffBook campaign);

    public CheffBook findByCheffBookId(Long id);

    public List<CheffBook> findAll();

    public List<CheffBook> findByUser(User user);

}
