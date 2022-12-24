package com.demo.droneservice.repository;

import com.demo.droneservice.modal.Drone;
import com.demo.droneservice.util.DroneState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;


import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Transactional
public interface DroneRepository extends JpaRepository<Drone,String> {
    Optional<Drone> findByDroneSerialNumber(String serialNumber);
    Optional<List<Drone>> findAllByDroneStateIn(@Param("drone_state") Collection<DroneState> state);

}
