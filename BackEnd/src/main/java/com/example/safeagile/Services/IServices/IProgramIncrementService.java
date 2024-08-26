package com.example.safeagile.Services.IServices;

import com.example.safeagile.Models.ProgramIncrement;

import java.util.List;
import java.util.Optional;

public interface IProgramIncrementService {

    // Create or Update Program Increment
    ProgramIncrement createOrUpdateProgramIncrement(ProgramIncrement programIncrement);

    // Read a Program Increment by ID
    Optional<ProgramIncrement> getProgramIncrementById(String id);

    // Get all Program Increments
    List<ProgramIncrement> getAllProgramIncrements();

    // Delete a Program Increment by ID
    void deleteProgramIncrement(String id);
}
