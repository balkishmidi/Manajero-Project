package com.example.safeagile.Services.ServicesImp;

import com.example.safeagile.Models.Task;
import com.example.safeagile.Models.UserStory;
import com.example.safeagile.Repositories.ITaskRepository;
import com.example.safeagile.Repositories.IUserStoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserStoryServiceImplTest {

    @Mock
    private IUserStoryRepository userStoryRepository;

    @Mock
    private ITaskRepository taskRepository;

    @InjectMocks
    private UserStoryServiceImpl userStoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUserStory() {
        UserStory userStory = new UserStory();
        when(userStoryRepository.save(any(UserStory.class))).thenReturn(userStory);

        UserStory createdUserStory = userStoryService.createUserStory(userStory);
        assertNotNull(createdUserStory);
        verify(userStoryRepository, times(1)).save(userStory);
    }

    @Test
    void testCreateUserStoryWithTasks() {
        Task task = new Task();
        task.set_id("taskId");
        UserStory userStory = new UserStory();
        userStory.setTasks(Collections.singletonList(task));

        when(taskRepository.findById(anyString())).thenReturn(Optional.of(task));
        when(userStoryRepository.save(any(UserStory.class))).thenReturn(userStory);

        UserStory createdUserStory = userStoryService.createUserStory(userStory);
        assertNotNull(createdUserStory);
        assertEquals(1, createdUserStory.getTasks().size());
        verify(taskRepository, times(1)).findById("taskId");
        verify(userStoryRepository, times(1)).save(userStory);
    }

    @Test
    void testCreateUserStories() {
        UserStory userStory1 = new UserStory();
        UserStory userStory2 = new UserStory();
        List<UserStory> userStories = Arrays.asList(userStory1, userStory2);

        when(userStoryRepository.save(any(UserStory.class))).thenAnswer(invocation -> invocation.getArgument(0));

        List<UserStory> createdUserStories = userStoryService.createUserStories(userStories);
        assertEquals(userStories.size(), createdUserStories.size());
        verify(userStoryRepository, times(userStories.size())).save(any(UserStory.class));
    }

    @Test
    void testGetUserStoryById() {
        UserStory userStory = new UserStory();
        when(userStoryRepository.findById(anyString())).thenReturn(Optional.of(userStory));

        UserStory foundUserStory = userStoryService.getUserStoryById("1");
        assertNotNull(foundUserStory);
        assertEquals(userStory, foundUserStory);
        verify(userStoryRepository, times(1)).findById("1");
    }

    @Test
    void testGetAllUserStories() {
        List<UserStory> userStories = Arrays.asList(new UserStory(), new UserStory());
        when(userStoryRepository.findAll()).thenReturn(userStories);

        List<UserStory> allUserStories = userStoryService.getAllUserStories();
        assertEquals(2, allUserStories.size());
        verify(userStoryRepository, times(1)).findAll();
    }

    @Test
    void testUpdateUserStory() {
        UserStory userStory = new UserStory();
        userStory.setId("1");
        when(userStoryRepository.existsById(anyString())).thenReturn(true);
        when(userStoryRepository.save(any(UserStory.class))).thenReturn(userStory);

        UserStory updatedUserStory = userStoryService.updateUserStory("1", userStory);
        assertNotNull(updatedUserStory);
        assertEquals("1", updatedUserStory.getId());
        verify(userStoryRepository, times(1)).save(userStory);
    }

    @Test
    void testUpdateUserStoryNotFound() {
        UserStory userStory = new UserStory();
        when(userStoryRepository.existsById(anyString())).thenReturn(false);

        UserStory updatedUserStory = userStoryService.updateUserStory("1", userStory);
        assertNull(updatedUserStory);
        verify(userStoryRepository, times(0)).save(userStory);
    }

    @Test
    void testDeleteUserStory() {
        userStoryService.deleteUserStory("1");
        verify(userStoryRepository, times(1)).deleteById("1");
    }

    @Test
    void testGetuscout() {
        when(userStoryRepository.count()).thenReturn(10L);

        Map<String, Integer> stats = userStoryService.getuscout();
        assertEquals(10, stats.get("Total US"));
        verify(userStoryRepository, times(1)).count();
    }
}
