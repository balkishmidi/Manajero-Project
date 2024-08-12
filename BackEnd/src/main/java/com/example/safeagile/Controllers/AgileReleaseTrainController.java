package com.example.safeagile.Controllers;

import com.example.safeagile.Models.AgileReleaseTrain;
import com.example.safeagile.Services.IServices.IAgileReleaseTrainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/art")
@RequiredArgsConstructor
@CrossOrigin("*")
@Slf4j
public class AgileReleaseTrainController {
    @Autowired
    private IAgileReleaseTrainService agileReleaseTrainService;

    @PostMapping
    public ResponseEntity<AgileReleaseTrain> createOrUpdateAgileReleaseTrain(@RequestBody AgileReleaseTrain agileReleaseTrain) {
        AgileReleaseTrain savedAgileReleaseTrain = agileReleaseTrainService.createOrUpdateAgileReleaseTrain(agileReleaseTrain);
        return new ResponseEntity<>(savedAgileReleaseTrain, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgileReleaseTrain> getAgileReleaseTrainById(@PathVariable("id") String id) {
        Optional<AgileReleaseTrain> agileReleaseTrain = agileReleaseTrainService.getAgileReleaseTrainById(id);
        return agileReleaseTrain.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<AgileReleaseTrain>> getAllAgileReleaseTrains() {
        List<AgileReleaseTrain> agileReleaseTrains = agileReleaseTrainService.getAllAgileReleaseTrains();
        return new ResponseEntity<>(agileReleaseTrains, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgileReleaseTrainById(@PathVariable("id") String id) {
        agileReleaseTrainService.deleteAgileReleaseTrainById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
