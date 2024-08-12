package com.example.safeagile.Services.ServicesImp;

import com.example.safeagile.Models.Feature;
import com.example.safeagile.Models.UserStory;

import com.example.safeagile.Repositories.IFeatureRepository;
import com.example.safeagile.Repositories.IUserStoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeatureService {
    @Autowired
    private IFeatureRepository featureRepository;
    @Autowired
    private IUserStoryRepository userStoryRepository; // Add this line to handle UserStories

    public List<Feature> getAllFeatures() {
        return featureRepository.findAll();
    }

    public Optional<Feature> getFeatureById(String id) {
        return featureRepository.findById(id);
    }


    public Feature createOrUpdateFeature(Feature feature) {
        List<UserStory> userStories = new ArrayList<>();
        for (UserStory userStory : feature.getUserStories()) {
            // Fetch the full UserStory object if needed
            UserStory existingUserStory = userStoryRepository.findById(userStory.getId()).orElse(null);
            if (existingUserStory != null) {
                userStories.add(existingUserStory);
            } else {
                // Handle case where user story is not found
                throw new ResourceNotFoundException("UserStory with ID " + userStory.getId() + " not found");
            }
        }
        feature.setUserStories(userStories);
        return featureRepository.save(feature);
    }

    public void deleteFeature(String id) {
        featureRepository.deleteById(id);
    }
}
