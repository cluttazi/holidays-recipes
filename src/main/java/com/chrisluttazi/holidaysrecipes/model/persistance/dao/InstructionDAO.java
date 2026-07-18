package com.chrisluttazi.holidaysrecipes.model.persistance.dao;

import com.chrisluttazi.holidaysrecipes.model.Instruction;
import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.transaction.Transactional;

@Transactional
public interface InstructionDAO extends JpaRepository<Instruction, Long> {
    @SuppressWarnings("unchecked")
    public Instruction save(Instruction holiday);

    public Instruction findByInstructionId(Long id);

}
