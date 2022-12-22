package com.demo.droneservice.controller;

import com.demo.droneservice.dto.request.DroneRegisterDTO;
import com.demo.droneservice.dto.request.LoadDroneDTO;
import com.demo.droneservice.dto.response.ResponseDTO;
import com.demo.droneservice.service.DroneServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(path="/api/demo/drone")
@Slf4j
public class DroneController {

    @Autowired
    private DroneServiceImpl droneService;

    @PostMapping(path="/register", consumes = "application/json", produces = "application/json")
    @Operation(summary = "Registering a Drone")
    public ResponseEntity<ResponseDTO> registerDrone(
            @Valid @NotNull @RequestBody DroneRegisterDTO droneRequest) {
        log.info("Starting DroneController -> registerDrone");
        ResponseDTO response = droneService.registerDrone(droneRequest);
        log.info("Ending DroneController -> registerDrone");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PostMapping(path="/load", consumes = "application/json", produces = "application/json")
    @Operation(summary = "Loading a Drone with medication")
    public ResponseEntity<ResponseDTO> loadingDroneWithMedication(
            @Valid @NotNull @RequestBody LoadDroneDTO loadDroneRequest) {
        log.info("Starting DroneController -> loadingDroneWithMedication");
        ResponseDTO response = droneService.loadingDroneWithMedication(loadDroneRequest);
        log.info("Ending DroneController -> loadingDroneWithMedication");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
