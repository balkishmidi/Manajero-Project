package com.example.safeagile.Services.ServicesImp;

import com.example.safeagile.Models.AgileReleaseTrain;
import com.example.safeagile.Models.ProgramIncrement;
import com.example.safeagile.Models.Sprint;
import com.example.safeagile.Repositories.IAgileReleaseTrainRepository;
import com.example.safeagile.Repositories.IProgramIncrementRepository;
import com.example.safeagile.Repositories.ISprintRepository;
import com.example.safeagile.Services.IServices.IAgileReleaseTrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AgileReleaseTrainService implements IAgileReleaseTrainService {

    @Autowired
    private IAgileReleaseTrainRepository agileReleaseTrainRepository;

    @Autowired
    private IProgramIncrementRepository programIncrementRepository;

    @Autowired
    private ISprintRepository sprintRepository;

    @Override
    public AgileReleaseTrain createOrUpdateAgileReleaseTrain(AgileReleaseTrain agileReleaseTrain) {
        // Resolve ProgramIncrement references
        List<ProgramIncrement> resolvedPIs = agileReleaseTrain.getProgramIncrements().stream()
                .map(pi -> programIncrementRepository.findById(pi.getId()).orElse(null))
                .collect(Collectors.toList());

        // Resolve Sprint references
        List<Sprint> resolvedSprints = agileReleaseTrain.getSprints().stream()
                .map(sprint -> sprintRepository.findById(sprint.getId()).orElse(null))
                .collect(Collectors.toList());

        // Set resolved references back to AgileReleaseTrain
        agileReleaseTrain.setProgramIncrements(resolvedPIs);
        agileReleaseTrain.setSprints(resolvedSprints);

        return agileReleaseTrainRepository.save(agileReleaseTrain);
    }

    @Override
    public Optional<AgileReleaseTrain> getAgileReleaseTrainById(String id) {
        return agileReleaseTrainRepository.findById(id);
    }

    @Override
    public List<AgileReleaseTrain> getAllAgileReleaseTrains() {
        return agileReleaseTrainRepository.findAll();
    }

    @Override
    public void deleteAgileReleaseTrainById(String id) {
        agileReleaseTrainRepository.deleteById(id);
    }
}