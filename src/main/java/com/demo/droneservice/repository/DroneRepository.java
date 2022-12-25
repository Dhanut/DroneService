package com.demo.droneservice.repository;

import com.demo.droneservice.modal.Drone;
import com.demo.droneservice.util.DroneState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface DroneRepository extends JpaRepository<Drone,String> {

    /**
     * Find Drone in the database using given drone_serial_number
     * **/
    Optional<Drone> findByDroneSerialNumber(String serialNumber);

    /**
     * Find All Drones in the database using given list of states of drones
     * **/
    List<Drone> findAllByDroneStateIn(@Param("drone_state") Collection<DroneState> state);

}
