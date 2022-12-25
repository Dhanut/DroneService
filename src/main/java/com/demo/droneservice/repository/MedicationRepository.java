package com.demo.droneservice.repository;


import com.demo.droneservice.modal.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface MedicationRepository extends JpaRepository<Medication,String> {
    /**
     * Find Medication in the database using given medication-code
     * **/
    Optional<Medication> findByMedicationCode(String medicationCode);
}
