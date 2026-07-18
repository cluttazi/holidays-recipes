package com.chrisluttazi.holidaysrecipes.model.persistance.dao;

import com.chrisluttazi.holidaysrecipes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.transaction.Transactional;
import java.util.List;

@Transactional
public interface UserDAO extends JpaRepository<User, String> {

    @SuppressWarnings("unchecked")
    public User save(User user);

    public User findByUsername(String username);

    public User findByUserId(Long id);

    public long count();

    public List<User> findAll();

}
