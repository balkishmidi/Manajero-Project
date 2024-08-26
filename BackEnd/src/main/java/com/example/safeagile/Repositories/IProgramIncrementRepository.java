package com.example.safeagile.Repositories;

import com.example.safeagile.Models.ProgramIncrement;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IProgramIncrementRepository extends MongoRepository<ProgramIncrement,String> {
}
