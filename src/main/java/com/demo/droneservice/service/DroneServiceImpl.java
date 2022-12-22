package com.demo.droneservice.service;

import com.demo.droneservice.dto.request.DroneRegisterDTO;
import com.demo.droneservice.dto.request.LoadDroneDTO;
import com.demo.droneservice.dto.request.MedicationDTO;
import com.demo.droneservice.dto.response.ResponseDTO;
import com.demo.droneservice.modal.Drone;
import com.demo.droneservice.modal.LoadDrone;
import com.demo.droneservice.modal.Medication;
import com.demo.droneservice.repository.DroneRepository;
import com.demo.droneservice.repository.LoadDroneRepository;
import com.demo.droneservice.repository.MedicationRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.demo.droneservice.util.DroneServiceConstants.*;
import static com.demo.droneservice.util.DroneState.LOADED;
import static com.demo.droneservice.util.DroneState.LOADING;

@Service
@Slf4j
public class DroneServiceImpl implements DroneService{

    @Autowired
    DroneRepository droneRepository;

    @Autowired
    MedicationRepository medicationRepository;
    @Autowired
    LoadDroneRepository loadDroneRepository;

    @Override
    public ResponseDTO registerDrone(DroneRegisterDTO droneRegisterRequest) {
        log.info("Starting DroneServiceImpl -> registerDrone");
        Drone droneModal = Drone.builder()
                .droneSerialNumber(droneRegisterRequest.getDroneSerialNumber())
                .droneModel(droneRegisterRequest.getDroneModel())
                .droneWeight(droneRegisterRequest.getDroneWeight())
                .droneBatteryCapacity(droneRegisterRequest.getDroneBatteryCapacity())
                .droneState(droneRegisterRequest.getDroneState()).
                build();
        droneRepository.save(droneModal);
        log.info("Creating Drone Item : {}",droneModal);
        log.info("Ending DroneServiceImpl -> registerDrone");
        return ResponseDTO.builder()
                .status(OK)
                .message(REGISTRED_SUCCESSFULLY)
                .data(droneModal)
                .build();
    }

    @Override
    public ResponseDTO loadingDroneWithMedication(LoadDroneDTO loadDroneRequest) {
        log.info("Starting DroneServiceImpl -> loadingDroneWithMedication");

        createMedicationEntries(loadDroneRequest.getLoadingMedicationList());

        //load can be performed if a drone in "LOADING" state only
        //changeDroneStateToLoad(loadDroneRequest.getLoadingDroneSerialNumber());

        Optional<Drone> drone = droneRepository.findByDroneSerialNumber(loadDroneRequest.getLoadingDroneSerialNumber());
        Double totalWeight =getTotalMedicationWeight(loadDroneRequest.getLoadingMedicationList());
        log.info("Total weight of medications : {}",totalWeight);

        if( (drone.isPresent())) {
            Double totalDroneWeight = totalWeight + drone.get().getDroneWeight();
            if( (DRONE_MAX_WEIGHT >= totalDroneWeight) && (LOADING.name().equalsIgnoreCase(drone.get().getDroneState().name()))){

                droneRepository.save(Drone.builder()
                        .droneSerialNumber(drone.get().getDroneSerialNumber())
                        .droneState(LOADED).
                        build());

                List<Medication> medicationList=null;
                ModelMapper modelMapper = new ModelMapper();
                for(MedicationDTO medicationItem : loadDroneRequest.getLoadingMedicationList()){
                    medicationList.add(modelMapper.map(medicationItem, Medication.class));
                }

                loadDroneRepository.save(LoadDrone.builder()
                        .loadingDroneSerialNumber(drone.get().getDroneSerialNumber())
                        .loadingDeliveryAddress(loadDroneRequest.getLoadingDeliveryAddress())
                        .loadingMedicationList(medicationList)
                        .loadingTotalQuantity(loadDroneRequest.getLoadingTotalQuantity())
                        .build());

                return ResponseDTO.builder()
                        .status(OK)
                        .message(LOADED_SUCCESSFULLY)
                        .data(loadDroneRequest)
                        .build();
            }else{
                log.info("error");
                return null;
            }

        }else{
            log.info("error");
            return null;
        }
    }

    private Double getTotalMedicationWeight(List<MedicationDTO> loadingMedicationList) {
        Double totalWeight=0.00;
        for(MedicationDTO medicationItem : loadingMedicationList){
            Optional<Medication> response = medicationRepository.findByMedicationCode(medicationItem.getMedicationCode());
            if(!response.isPresent()){
                log.error("NO record found");
            }else{
                totalWeight= totalWeight+response.get().getMedicationWeight();
            }

        }
        return totalWeight;
    }

    private void createMedicationEntries(List<MedicationDTO> loadingMedicationList){
        for(MedicationDTO medicationItem : loadingMedicationList){
            medicationRepository.save(Medication
                    .builder()
                            .medicationCode(medicationItem.getMedicationCode())
                            .medicationName(medicationItem.getMedicationName())
                            .medicationWeight(medicationItem.getMedicationWeight())
                            .medicationImage(medicationItem.getMedicationImage())
                    .build());
        }
    }

   /** private void changeDroneStateToLoad(String loadingDroneSerialNumber) {
        droneRepository.setUpdateState(LOADING,loadingDroneSerialNumber);
    }**/
}
