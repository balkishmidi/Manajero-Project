package com.example.safeagile.Services.ServicesImp;

import com.example.safeagile.Models.ProgramIncrement;
import com.example.safeagile.Models.Sprint;
import com.example.safeagile.Repositories.IProgramIncrementRepository;
import com.example.safeagile.Repositories.ISprintRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProgramIncrementServiceTest {

    @Mock
    private IProgramIncrementRepository programIncrementRepository;

    @Mock
    private ISprintRepository sprintRepository;

    @InjectMocks
    private ProgramIncrementService programIncrementService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateOrUpdateProgramIncrement() {
        ProgramIncrement pi = new ProgramIncrement();
        Sprint sprint = new Sprint();
        sprint.setId("sprintId");
        pi.setSprints(List.of(sprint));

        when(sprintRepository.findById("sprintId")).thenReturn(Optional.of(sprint));
        when(programIncrementRepository.save(pi)).thenReturn(pi);

        ProgramIncrement result = programIncrementService.createOrUpdateProgramIncrement(pi);

        assertEquals(pi, result);
        verify(sprintRepository, times(1)).findById("sprintId");
        verify(programIncrementRepository, times(1)).save(pi);
    }

    @Test
    void testCreateOrUpdateProgramIncrement_SprintNotFound() {
        ProgramIncrement pi = new ProgramIncrement();
        Sprint sprint = new Sprint();
        sprint.setId("sprintId");
        pi.setSprints(List.of(sprint));

        when(sprintRepository.findById("sprintId")).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            programIncrementService.createOrUpdateProgramIncrement(pi);
        });

        assertEquals("Sprint with ID sprintId not found", thrown.getMessage());
        verify(sprintRepository, times(1)).findById("sprintId");
    }

    @Test
    void testGetProgramIncrementById() {
        String piId = "1";
        ProgramIncrement pi = new ProgramIncrement();
        when(programIncrementRepository.findById(piId)).thenReturn(Optional.of(pi));

        Optional<ProgramIncrement> result = programIncrementService.getProgramIncrementById(piId);

        assertTrue(result.isPresent());
        assertEquals(pi, result.get());
        verify(programIncrementRepository, times(1)).findById(piId);
    }

    @Test
    void testGetAllProgramIncrements() {
        List<ProgramIncrement> programIncrements = Arrays.asList(new ProgramIncrement(), new ProgramIncrement());
        when(programIncrementRepository.findAll()).thenReturn(programIncrements);

        List<ProgramIncrement> result = programIncrementService.getAllProgramIncrements();

        assertEquals(2, result.size());
        verify(programIncrementRepository, times(1)).findAll();
    }

    @Test
    void testDeleteProgramIncrement() {
        String piId = "1";

        programIncrementService.deleteProgramIncrement(piId);

        verify(programIncrementRepository, times(1)).deleteById(piId);
    }
}
