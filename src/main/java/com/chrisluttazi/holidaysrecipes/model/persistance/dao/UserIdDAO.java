package com.chrisluttazi.holidaysrecipes.model.persistance.dao;

import com.chrisluttazi.holidaysrecipes.model.UserId;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface UserIdDAO extends PagingAndSortingRepository<UserId, Long> {

    @SuppressWarnings("unchecked")
    public UserId save(UserId id);

    public List<UserId> findAll();

}
