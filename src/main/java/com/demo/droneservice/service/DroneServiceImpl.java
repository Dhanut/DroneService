package com.demo.droneservice.service;

import com.demo.droneservice.dto.request.DroneRegisterDTO;
import com.demo.droneservice.dto.response.DroneRegisterResponseDTO;
import com.demo.droneservice.modal.Drone;
import com.demo.droneservice.repository.DroneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.demo.droneservice.util.DroneServiceConstants.REGISTRED_SUCCESSFULLY;
import static com.demo.droneservice.util.DroneServiceConstants.SUCCESS;

@Service
public class DroneServiceImpl implements DroneService{

    @Autowired
    DroneRepository droneRepository;

    @Override
    public DroneRegisterResponseDTO registerDrone(DroneRegisterDTO drone) {
        Drone droneModal = Drone.builder()
                .serialNumber(drone.getSerialNumber())
                .droneModel(drone.getDroneModel())
                .weight(drone.getWeight())
                .batteryCapacity(drone.getBatteryCapacity())
                .droneState(drone.getDroneState()).
                build();
        droneRepository.save(droneModal);

        return DroneRegisterResponseDTO.builder()
                .status(SUCCESS)
                .message(REGISTRED_SUCCESSFULLY)
                .serialNumber(droneModal.getSerialNumber())
                .timestamp(LocalDateTime.now())
                .build();
    }
}
