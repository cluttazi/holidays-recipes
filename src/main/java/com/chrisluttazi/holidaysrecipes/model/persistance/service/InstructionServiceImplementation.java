package com.chrisluttazi.holidaysrecipes.model.persistance.service;

import com.chrisluttazi.holidaysrecipes.model.Instruction;
import com.chrisluttazi.holidaysrecipes.model.persistance.dao.InstructionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstructionServiceImplementation implements InstructionService {

    @Autowired
    private InstructionDAO instructionDAO;

    @Override
    public Instruction findByInstructionId(Long id) {
        return instructionDAO.findByInstructionId(id);
    }

    @Override
    public Instruction update(Instruction instruction) {
        if (instructionDAO.findByInstructionId(instruction.getInstructionId()) != null) {
            return instructionDAO.save(instruction);
        }
        return null;
    }

    @Override
    public Instruction create(Instruction instruction) {
        if (instructionDAO.findByInstructionId(instruction.getInstructionId()) != null) {
            return null;
        }
        return instructionDAO.save(instruction);
    }
}
