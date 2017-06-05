package com.chrisluttazi.holidaysrecipes.model.persistance.dao;

import com.chrisluttazi.holidaysrecipes.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface UserDAO extends PagingAndSortingRepository<User, Long> {

    @SuppressWarnings("unchecked")
    public User save(User user);

    public User findByUsername(String username);

    public User findByUserId(Long id);

    public long count();

    public List<User> findAll();

}
