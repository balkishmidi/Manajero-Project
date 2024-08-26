package com.example.safeagile.Repositories;

import com.example.safeagile.Models.AgileReleaseTrain;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IAgileReleaseTrainRepository extends MongoRepository<AgileReleaseTrain, String> {
}
