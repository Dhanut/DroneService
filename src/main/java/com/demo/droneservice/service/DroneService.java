package com.demo.droneservice.service;

import com.demo.droneservice.dto.request.DroneRegisterDTO;
import com.demo.droneservice.dto.request.LoadDroneDTO;
import com.demo.droneservice.dto.response.ResponseDTO;

public interface DroneService {

    ResponseDTO registerDrone(DroneRegisterDTO droneRegisterRequest);
    ResponseDTO loadingDroneWithMedication(LoadDroneDTO loadDroneRequest);
}
