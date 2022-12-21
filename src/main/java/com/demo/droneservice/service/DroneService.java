package com.demo.droneservice.service;

import com.demo.droneservice.dto.request.DroneRegisterDTO;
import com.demo.droneservice.dto.response.DroneRegisterResponseDTO;

public interface DroneService {

    DroneRegisterResponseDTO registerDrone(DroneRegisterDTO drone);
}
