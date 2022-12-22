package com.demo.droneservice.repository;

import com.demo.droneservice.modal.Drone;
import com.demo.droneservice.util.DroneState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface DroneRepository extends JpaRepository<Drone,String> {

    Optional<Drone> findByDroneSerialNumber(String serialNumber);

    /**@Modifying
    @Query(value = "update Drone d set d.droneState = : state where  d.droneSerialNumber= :serialno")
    void setUpdateState (@Param("state") DroneState loading, @Param("serialno") String serialno);**/
}
