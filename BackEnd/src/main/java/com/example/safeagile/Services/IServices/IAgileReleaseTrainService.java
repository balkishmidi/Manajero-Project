package com.example.safeagile.Services.IServices;

import com.example.safeagile.Models.AgileReleaseTrain;

import java.util.List;
import java.util.Optional;

public interface IAgileReleaseTrainService {
    AgileReleaseTrain createOrUpdateAgileReleaseTrain(AgileReleaseTrain agileReleaseTrain);
    Optional<AgileReleaseTrain> getAgileReleaseTrainById(String id);
    List<AgileReleaseTrain> getAllAgileReleaseTrains();
    void deleteAgileReleaseTrainById(String id);
}
