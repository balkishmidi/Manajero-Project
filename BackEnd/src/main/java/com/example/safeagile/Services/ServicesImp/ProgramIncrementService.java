package com.example.safeagile.Services.ServicesImp;

import com.example.safeagile.Models.ProgramIncrement;
import com.example.safeagile.Models.Sprint;
import com.example.safeagile.Repositories.IProgramIncrementRepository;
import com.example.safeagile.Repositories.ISprintRepository;
import com.example.safeagile.Services.IServices.IProgramIncrementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service

public class ProgramIncrementService implements IProgramIncrementService {
    @Autowired
    private IProgramIncrementRepository programIncrementRepository;
    @Autowired
    private ISprintRepository sprintRepository;
    // Create or Update Program Increment
    @Override
        public ProgramIncrement createOrUpdateProgramIncrement(ProgramIncrement pi) {
            List<Sprint> sprints = new ArrayList<>();
            for (Sprint sprint : pi.getSprints()) {
                // Fetch the full Sprint object if needed
                Sprint existingSprint = sprintRepository.findById(sprint.getId()).orElse(null);
                if (existingSprint != null) {
                    sprints.add(existingSprint);
                } else {
                    // Handle case where sprint is not found
                    throw new ResourceNotFoundException("Sprint with ID " + sprint.getId() + " not found");
                }
            }
            pi.setSprints(sprints);
            return programIncrementRepository.save(pi);

    }

    // Read a Program Increment by ID
    public Optional<ProgramIncrement> getProgramIncrementById(String id) {
        return programIncrementRepository.findById(id);
    }

    // Get all Program Increments
    public List<ProgramIncrement> getAllProgramIncrements() {
        return programIncrementRepository.findAll();
    }

    // Delete a Program Increment by ID
    public void deleteProgramIncrement(String id) {
        programIncrementRepository.deleteById(id);
    }
}
