package com.demo.droneservice.repository;

import com.demo.droneservice.modal.LoadDrone;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface LoadDroneRepository extends JpaRepository<LoadDrone,Long> {
}
