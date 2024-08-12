package com.example.safeagile.Repositories;

import com.example.safeagile.Models.Epic;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IEpicRepository extends MongoRepository<Epic,String> {
}
