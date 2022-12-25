package com.demo.droneservice.repository;


import com.demo.droneservice.modal.LoadDrone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoadDroneRepository extends JpaRepository<LoadDrone,Long> {

    /**
     * Find Drone in the database using given drone_serial_number
     * **/
    List<LoadDrone> findByLoadingDroneSerialNumber(String serialNumber);
}
