package com.demo.droneservice.repository;

import com.demo.droneservice.modal.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public interface DroneRepository extends JpaRepository<Drone,String> {
    Optional<Drone> findByDroneSerialNumber(String serialNumber);

    @Query(value = "SELECT e FROM Drone e GROUP BY droneState HAVING droneState=:#{#IDLE?.name()}")
    Optional<List<Drone>> findAvailableDrones();
}
