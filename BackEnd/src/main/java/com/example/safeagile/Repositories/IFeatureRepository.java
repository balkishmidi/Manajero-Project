package com.example.safeagile.Repositories;

import com.example.safeagile.Models.Feature;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IFeatureRepository extends MongoRepository<Feature, String> {
}
