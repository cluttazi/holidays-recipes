package com.chrisluttazi.holidaysrecipes.model.persistance.service;

import com.chrisluttazi.holidaysrecipes.model.Instruction;
import org.springframework.security.access.prepost.PreAuthorize;

public interface InstructionService {
    public Instruction findByInstructionId(Long id);

    @PreAuthorize("hasAuthority('CHEFF')")
    public Instruction update(Instruction supplier);

    @PreAuthorize("hasAuthority('CHEFF')")
    public Instruction create(Instruction supplier);

}
