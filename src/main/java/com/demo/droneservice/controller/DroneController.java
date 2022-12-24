package com.demo.droneservice.controller;

import com.demo.droneservice.dto.request.DroneRegisterDTO;
import com.demo.droneservice.dto.request.LoadDroneDTO;
import com.demo.droneservice.dto.request.MedicationDTO;
import com.demo.droneservice.dto.response.ResponseDTO;
import com.demo.droneservice.service.DroneServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ResponseDTO> loadDroneWithMedication(
            @Valid @NotNull @RequestBody LoadDroneDTO loadDroneRequest)  {
        log.info("Starting DroneController -> loadDroneWithMedication");
        ResponseDTO response = droneService.loadDroneWithMedication(loadDroneRequest);
        log.info("Ending DroneController -> loadDroneWithMedication");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(path="/check/medications/{serialNumber}", produces = "application/json")
    @Operation(summary = "Checking available medications for a given drone")
    public ResponseEntity<ResponseDTO> checkLoadedMedications(
            @PathVariable("serialNumber")String serialNumber)  {
        log.info("Starting DroneController -> checkLoadedMedications");
        log.info("Serial Number :{}",serialNumber);
        ResponseDTO response = droneService.checkLoadedMedications(serialNumber);
        log.info("Ending DroneController -> checkLoadedMedications");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(path="/check/available", produces = "application/json")
    @Operation(summary = "Checking available drones")
    public ResponseEntity<ResponseDTO> checkAvailableDrones()  {
        log.info("Starting DroneController -> checkAvailableDrones");
        ResponseDTO response = droneService.checkAvailableDrones();
        log.info("Ending DroneController -> checkAvailableDrones");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(path="/check/batteryLevel/{serialNumber}", produces = "application/json")
    @Operation(summary = "Checking battery level for a given drone")
    public ResponseEntity<ResponseDTO> checkBatteryLevel(
            @PathVariable("serialNumber")String serialNumber)  {
        log.info("Starting DroneController -> checkBatteryLevel");
        log.info("Serial Number :{}",serialNumber);
        ResponseDTO response = droneService.checkBatteryLevel(serialNumber);
        log.info("Ending DroneController -> checkBatteryLevel");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @PostMapping(path="/load/create/medications", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ResponseDTO> createMedicationEntries(
            @Valid @NotNull @RequestBody MedicationDTO medicationDTO)  {

         droneService.createMedications(medicationDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

}
