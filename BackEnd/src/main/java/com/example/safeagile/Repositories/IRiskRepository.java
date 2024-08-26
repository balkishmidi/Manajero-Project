package com.example.safeagile.Repositories;

import com.example.safeagile.Models.Risk;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IRiskRepository extends MongoRepository<Risk,String> {
}
