package com.chrisluttazi.holidaysrecipes.model.persistance.dao;

import com.chrisluttazi.holidaysrecipes.model.UserId;
import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.transaction.Transactional;
import java.util.List;

@Transactional
public interface UserIdDAO extends JpaRepository<UserId, Long> {

    @SuppressWarnings("unchecked")
    public UserId save(UserId id);

    public List<UserId> findAll();

}
