package com.example.safeagile.Services.ServicesImp;

import com.example.safeagile.Models.Task;
import com.example.safeagile.Models.TaskStatus;
import com.example.safeagile.Repositories.ITaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TaskServiceImplTest {

    @Mock
    private ITaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @BeforeEach
    void setUp() {
        // MockitoAnnotations.openMocks(this); // May not be needed with @SpringBootTest
    }

    @Test
    void testCreateTask() {
        Task task = new Task();
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task createdTask = taskService.createTask(task);
        assertNotNull(createdTask, "Task should not be null after creation");
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void testGetTaskById() {
        Task task = new Task();
        when(taskRepository.findById(anyString())).thenReturn(Optional.of(task));

        Optional<Task> foundTask = taskService.getTaskById("1");
        assertTrue(foundTask.isPresent(), "Task should be present");
        assertEquals(task, foundTask.get(), "Retrieved task should match the expected task");
        verify(taskRepository, times(1)).findById("1");
    }

    @Test
    void testGetAllTasks() {
        when(taskRepository.findAll()).thenReturn(Arrays.asList(new Task(), new Task()));
        List<Task> tasks = taskService.getAllTasks();
        assertEquals(2, tasks.size(), "Should return exactly 2 tasks");
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void testUpdateTask() {
        Task task = new Task();
        task.set_id("1");
        when(taskRepository.existsById(anyString())).thenReturn(true);
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task updatedTask = taskService.updateTask("1", task);
        assertNotNull(updatedTask, "Updated task should not be null");
        assertEquals("1", updatedTask.get_id(), "Task ID should remain the same after update");
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void testUpdateTaskNotFound() {
        Task task = new Task();
        when(taskRepository.existsById(anyString())).thenReturn(false);

        Task updatedTask = taskService.updateTask("1", task);
        assertNull(updatedTask, "Task should be null if it doesn't exist in the repository");
        verify(taskRepository, times(0)).save(task);
        verify(taskRepository, times(1)).existsById(anyString());
    }

    @Test
    void testDeleteTask() {
        when(taskRepository.existsById(anyString())).thenReturn(true);

        taskService.deleteTask("1");
        verify(taskRepository, times(1)).deleteById("1");
    }

    @Test
    void testCalculateTaskStats() {
        Task task1 = new Task();
        task1.setStatus(TaskStatus.DONE);

        Task task2 = new Task();
        task2.setStatus(TaskStatus.IN_PROGRESS);

        Task task3 = new Task();
        task3.setStatus(TaskStatus.TO_TEST);

        List<Task> tasks = Arrays.asList(task1, task2, task3);
        when(taskRepository.findAll()).thenReturn(tasks);

        Map<TaskStatus, Long> stats = taskService.calculateTaskStats();
        assertEquals(1, stats.get(TaskStatus.DONE), "DONE tasks count should be 1");
        assertEquals(1, stats.get(TaskStatus.IN_PROGRESS), "IN_PROGRESS tasks count should be 1");
        assertEquals(1, stats.get(TaskStatus.TO_TEST), "TO_TEST tasks count should be 1");
        assertEquals(0, stats.getOrDefault(TaskStatus.TO_REDO, 0L), "TO_REDO tasks count should be 0");
        assertEquals(0, stats.getOrDefault(TaskStatus.TO_IMPROVE, 0L), "TO_IMPROVE tasks count should be 0");
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void testGetTaskStats() {
        when(taskRepository.count()).thenReturn(5L);

        Map<String, Integer> stats = taskService.getTaskStats();
        assertEquals(5, stats.get("Total Tasks"), "Total tasks should be 5");
        verify(taskRepository, times(1)).count();
    }
}
