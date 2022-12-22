package com.demo.droneservice.repository;


import com.demo.droneservice.modal.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface MedicationRepository extends JpaRepository<Medication,String> {

    Optional<Medication> findByMedicationCode(String medicationCode);
}
