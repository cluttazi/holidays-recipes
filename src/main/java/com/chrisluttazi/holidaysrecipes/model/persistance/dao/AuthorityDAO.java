package com.chrisluttazi.holidaysrecipes.model.persistance.dao;

import com.chrisluttazi.holidaysrecipes.model.Authority;
import com.chrisluttazi.holidaysrecipes.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface AuthorityDAO extends PagingAndSortingRepository<Authority, Long> {

    @SuppressWarnings("unchecked")
    public Authority save(Authority user);

    public Authority findByUser(User user);

    public long count();

    public List<Authority> findAll();

    public void delete(Long id);

}
