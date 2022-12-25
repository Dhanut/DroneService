package com.demo.droneservice.schedular;

import com.demo.droneservice.modal.Drone;
import com.demo.droneservice.repository.DroneRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Slf4j
public class CheckBatteryLevel {

    @Autowired
    DroneRepository droneRepository;

    /**
     *Check and log the battery level of each drone in database periodically (at every 10000 millisecond)
     * **/
    @Scheduled(fixedRate = 10000)
    public void checkBatteryLevel() {
        List<Drone> drones = droneRepository.findAll();
        drones.forEach(b -> log.info("Battery level for Drone {} is {}%",b.getDroneSerialNumber(),b.getDroneBatteryCapacity()));
    }
}
