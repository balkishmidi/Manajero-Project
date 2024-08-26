package com.example.safeagile.Repositories;

import com.example.safeagile.Models.Team;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ITeamRepository extends MongoRepository<Team,String> {
}
