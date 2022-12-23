package com.demo.droneservice.repository;

import com.demo.droneservice.modal.Drone;
import com.demo.droneservice.modal.LoadDrone;
import com.demo.droneservice.modal.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public interface LoadDroneRepository extends JpaRepository<LoadDrone,Long> {

    Optional<List<LoadDrone>> findByLoadingDroneSerialNumber(String serialNumber);
}
