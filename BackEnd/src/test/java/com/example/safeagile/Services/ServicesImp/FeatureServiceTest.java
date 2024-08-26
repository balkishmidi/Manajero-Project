package com.example.safeagile.Services.ServicesImp;

import com.example.safeagile.Models.Feature;
import com.example.safeagile.Models.UserStory;
import com.example.safeagile.Repositories.IFeatureRepository;
import com.example.safeagile.Repositories.IUserStoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FeatureServiceTest {

    @Mock
    private IFeatureRepository featureRepository;

    @Mock
    private IUserStoryRepository userStoryRepository;

    @InjectMocks
    private FeatureService featureService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllFeatures() {
        List<Feature> features = Arrays.asList(new Feature(), new Feature());
        when(featureRepository.findAll()).thenReturn(features);

        List<Feature> result = featureService.getAllFeatures();

        assertEquals(2, result.size());
        verify(featureRepository, times(1)).findAll();
    }

    @Test
    void testGetFeatureById() {
        String featureId = "1";
        Feature feature = new Feature();
        when(featureRepository.findById(featureId)).thenReturn(Optional.of(feature));

        Optional<Feature> result = featureService.getFeatureById(featureId);

        assertTrue(result.isPresent());
        assertEquals(feature, result.get());
        verify(featureRepository, times(1)).findById(featureId);
    }

    @Test
    void testCreateOrUpdateFeature() {
        Feature feature = new Feature();
        UserStory userStory = new UserStory();
        userStory.setId("userStoryId");
        feature.setUserStories(Collections.singletonList(userStory));

        when(userStoryRepository.findById("userStoryId")).thenReturn(Optional.of(userStory));
        when(featureRepository.save(feature)).thenReturn(feature);

        Feature result = featureService.createOrUpdateFeature(feature);

        assertEquals(feature, result);
        verify(userStoryRepository, times(1)).findById("userStoryId");
        verify(featureRepository, times(1)).save(feature);
    }

    @Test
    void testCreateOrUpdateFeature_UserStoryNotFound() {
        Feature feature = new Feature();
        UserStory userStory = new UserStory();
        userStory.setId("userStoryId");
        feature.setUserStories(Collections.singletonList(userStory));

        when(userStoryRepository.findById("userStoryId")).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            featureService.createOrUpdateFeature(feature);
        });

        assertEquals("UserStory with ID userStoryId not found", thrown.getMessage());
        verify(userStoryRepository, times(1)).findById("userStoryId");
    }

    @Test
    void testDeleteFeature() {
        String featureId = "1";

        featureService.deleteFeature(featureId);

        verify(featureRepository, times(1)).deleteById(featureId);
    }

    @Test
    void testGetFeaturesCount() {
        when(featureRepository.count()).thenReturn(5L);

        Map<String, Integer> result = featureService.getfeaturescount();

        assertEquals(1, result.size());
        assertEquals(5, result.get("Total feature"));
        verify(featureRepository, times(1)).count();
    }
}
