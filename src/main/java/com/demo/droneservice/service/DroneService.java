package com.demo.droneservice.service;

import com.demo.droneservice.dto.request.DroneRegisterDTO;
import com.demo.droneservice.dto.request.LoadDroneDTO;
import com.demo.droneservice.dto.response.ResponseDTO;

public interface DroneService {

    ResponseDTO registerDrone(DroneRegisterDTO droneRegisterRequest);
    ResponseDTO loadDroneWithMedication(LoadDroneDTO loadDroneRequest);
    ResponseDTO checkLoadedMedications(String droneSerialNumber);
    ResponseDTO checkBatteryLevel(String droneSerialNumber);
    ResponseDTO checkAvailableDrones();
}
