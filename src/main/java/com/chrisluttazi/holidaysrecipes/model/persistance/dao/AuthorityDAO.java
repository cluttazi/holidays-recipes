package com.chrisluttazi.holidaysrecipes.model.persistance.dao;

import com.chrisluttazi.holidaysrecipes.model.Authority;
import com.chrisluttazi.holidaysrecipes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.transaction.Transactional;
import java.util.List;

@Transactional
public interface AuthorityDAO extends JpaRepository<Authority, Integer> {

    @SuppressWarnings("unchecked")
    public Authority save(Authority user);

    public Authority findByUser(User user);

    public long count();

    public List<Authority> findAll();

}
