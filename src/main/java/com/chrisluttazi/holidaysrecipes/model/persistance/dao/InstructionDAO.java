package com.chrisluttazi.holidaysrecipes.model.persistance.dao;

import com.chrisluttazi.holidaysrecipes.model.Instruction;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;

@Transactional
public interface InstructionDAO extends PagingAndSortingRepository<Instruction, Long> {
    @SuppressWarnings("unchecked")
    public Instruction save(Instruction holiday);

    public Instruction findByInstructionId(Long id);

}
