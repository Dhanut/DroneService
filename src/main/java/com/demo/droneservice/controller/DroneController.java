package com.demo.droneservice.controller;

import com.demo.droneservice.dto.request.DroneRegisterDTO;
import com.demo.droneservice.dto.response.DroneRegisterResponseDTO;
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

@RestController
@RequestMapping(path="/api/demo/drone")
@Slf4j
public class DroneController {

    @Autowired
    private DroneServiceImpl droneService;

    @PostMapping(path="/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<DroneRegisterResponseDTO> registerDrone(
            @Valid @NotNull @RequestBody DroneRegisterDTO dronerequest) {
        log.info("Starting DroneController -> registerDrone");
        DroneRegisterResponseDTO newDrone = droneService.registerDrone(dronerequest);
        log.info("Ending DroneController -> registerDrone");
        return new ResponseEntity<DroneRegisterResponseDTO>(newDrone, HttpStatus.CREATED);
    }
}
