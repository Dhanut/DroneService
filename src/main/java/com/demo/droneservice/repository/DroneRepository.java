package com.demo.droneservice.repository;

import com.demo.droneservice.modal.Drone;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface DroneRepository extends JpaRepository<Drone,String> {
}
