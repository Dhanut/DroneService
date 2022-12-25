package com.demo.droneservice.service;

import com.demo.droneservice.dto.request.DroneRegisterDTO;
import com.demo.droneservice.dto.request.LoadDroneDTO;
import com.demo.droneservice.dto.response.ResponseDTO;
import com.demo.droneservice.exception.CustomBusinessException;

public interface DroneService {

    ResponseDTO registerDrone(DroneRegisterDTO droneRegisterRequest);
    ResponseDTO loadDroneWithMedication(LoadDroneDTO loadDroneRequest) throws CustomBusinessException;
    ResponseDTO checkLoadedMedications(String droneSerialNumber) throws CustomBusinessException;
    ResponseDTO checkBatteryLevel(String droneSerialNumber) throws CustomBusinessException;
    ResponseDTO checkAvailableDrones() throws CustomBusinessException;
}
