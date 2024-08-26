package com.example.safeagile.Services.ServicesImp;

import com.example.safeagile.Models.Sprint;
import com.example.safeagile.Models.UserStory;
import com.example.safeagile.Repositories.ISprintRepository;
import com.example.safeagile.Repositories.IUserStoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SprintServicesImplTest {

    @Mock
    private ISprintRepository sprintRepository;

    @Mock
    private IUserStoryRepository userStoryRepository;

    @InjectMocks
    private SprintServicesImpl sprintServicesImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateSprint_UserStoryNotFound() {
        Sprint sprint = new Sprint();
        UserStory userStory = new UserStory();
        userStory.setId("invalidId");
        sprint.setUserStory(Collections.singletonList(userStory));

        when(userStoryRepository.findById(anyString())).thenReturn(Optional.empty());

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            sprintServicesImpl.createSprint(sprint);
        });

        assertTrue(thrown.getMessage().contains("UserStory with ID invalidId not found"));
        verify(userStoryRepository, times(1)).findById("invalidId");
    }

    @Test
    void testCreateSprint_ValidUserStories() {
        Sprint sprint = new Sprint();
        UserStory userStory = new UserStory();
        userStory.setId("validId");
        sprint.setUserStory(Collections.singletonList(userStory));

        when(userStoryRepository.findById("validId")).thenReturn(Optional.of(userStory));
        when(sprintRepository.save(sprint)).thenReturn(sprint);

        Sprint result = sprintServicesImpl.createSprint(sprint);

        // Print for debugging if necessary
        System.out.println("Result of createSprint: " + result);

        assertNotNull(result, "Expected result to be not null");
        assertEquals(sprint, result, "Expected result to match the input sprint");
        verify(userStoryRepository, times(1)).findById("validId");
        verify(sprintRepository, times(1)).save(sprint);
    }

    @Test
    void testGetSprintById() {
        String sprintId = "1";
        Sprint sprint = new Sprint();
        when(sprintRepository.findById(sprintId)).thenReturn(Optional.of(sprint));

        Sprint result = sprintServicesImpl.getSprintById(sprintId);

        assertEquals(sprint, result);
        verify(sprintRepository, times(1)).findById(sprintId);
    }

    @Test
    void testGetAllSprints() {
        List<Sprint> sprints = Arrays.asList(new Sprint(), new Sprint());
        when(sprintRepository.findAll()).thenReturn(sprints);

        List<Sprint> result = sprintServicesImpl.getAllSprints();

        assertEquals(2, result.size());
        verify(sprintRepository, times(1)).findAll();
    }

    @Test
    void testUpdateSprint() {
        String sprintId = "1";
        Sprint existingSprint = new Sprint();
        existingSprint.setName("Old Sprint");
        existingSprint.setGoal("Old Goal");

        Sprint newSprintData = new Sprint();
        newSprintData.setName("New Sprint");
        newSprintData.setGoal("New Goal");

        when(sprintRepository.findById(sprintId)).thenReturn(Optional.of(existingSprint));
        when(sprintRepository.save(existingSprint)).thenReturn(existingSprint);

        Sprint result = sprintServicesImpl.updateSprint(sprintId, newSprintData);

        assertEquals("New Sprint", result.getName());
        assertEquals("New Goal", result.getGoal());
        verify(sprintRepository, times(1)).findById(sprintId);
        verify(sprintRepository, times(1)).save(existingSprint);
    }

    @Test
    void testUpdateSprint_SprintNotFound() {
        String sprintId = "1";
        Sprint newSprintData = new Sprint();
        newSprintData.setName("New Sprint");

        when(sprintRepository.findById(sprintId)).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            sprintServicesImpl.updateSprint(sprintId, newSprintData);
        });

        assertEquals("Sprint not found with id: " + sprintId, thrown.getMessage());
    }

    @Test
    void testDeleteSprint() {
        String sprintId = "1";

        sprintServicesImpl.deleteSprint(sprintId);

        verify(sprintRepository, times(1)).deleteById(sprintId);
    }

    @Test
    void testGetSprintStats() {
        long totalSprints = 5;
        when(sprintRepository.count()).thenReturn(totalSprints);

        Map<String, Integer> result = sprintServicesImpl.getSprintStats();

        assertEquals(1, result.size());
        assertEquals((int) totalSprints, result.get("Total Sprints"));
        verify(sprintRepository, times(1)).count();
    }
}
