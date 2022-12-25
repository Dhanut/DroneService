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
import com.demo.droneservice.util.DroneState;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.demo.droneservice.util.DroneServiceConstants.*;
import static com.demo.droneservice.util.DroneState.*;

@Service
@Slf4j
public class DroneServiceImpl implements DroneService{

    @Autowired
    DroneRepository droneRepository;

    @Autowired
    MedicationRepository medicationRepository;
    @Autowired
    LoadDroneRepository loadDroneRepository;

    ResponseDTO responseDTO;


    /**Input - DroneRegisterDTO
     * Register a new Drone in the database or even can use to update an existing drone attribute values
     * Return - ResponseDTO
     * **/
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
        log.info("Response of registerDrone : {}",droneModal);
        log.info("Ending DroneServiceImpl -> registerDrone");
        return ResponseDTO.builder()
                .status(OK)
                .message(REGISTRED_SUCCESSFULLY)
                .data(droneModal)
                .build();
    }

    /**Input - LoadDroneDTO
     * Load medications to a Drone
     * The Drone should be in IDLE or LOADING state only.
     * And battery capacity should be more than 25% and Drone max weight can not exceed 500g.
     * During loading if the Drone reached to 500g limit, state of drone should be updated to "LOADED" and can not load medication to this drone again.
     * Same medication items can be loaded to a same drone many times if the Drone has not reached to 500g limit
     * Return - ResponseDTO
     * **/
    @Override
    public ResponseDTO loadDroneWithMedication(LoadDroneDTO loadDroneRequest){
        log.info("Starting DroneServiceImpl -> loadingDroneWithMedication");

        Optional<Drone> drone = droneRepository.findByDroneSerialNumber(loadDroneRequest.getLoadingDroneSerialNumber());
        Double totalMedicationWeight =getTotalMedicationWeight(loadDroneRequest.getLoadingMedicationList());
        log.info("Total weight of medications : {}",totalMedicationWeight);

        if( (drone.isPresent())) {
            Double totalDroneWeight = totalMedicationWeight + drone.get().getDroneWeight();
            log.info("Total weight of Drone with medications : {}",totalDroneWeight);
            if( (DRONE_MAX_WEIGHT >= totalDroneWeight) &&
                    ((LOADING.name().equalsIgnoreCase(drone.get().getDroneState().name()))
                            || (IDLE.name().equalsIgnoreCase(drone.get().getDroneState().name()))) &&
                    (BATTERY_CAPACITY_THRESHOLD < drone.get().getDroneBatteryCapacity())){

                Drone loadedDrone = Drone.builder()
                        .droneSerialNumber(drone.get().getDroneSerialNumber())
                        .droneWeight(totalDroneWeight)
                        .droneModel(drone.get().getDroneModel())
                        .droneBatteryCapacity(drone.get().getDroneBatteryCapacity()).
                        build();
                if(DRONE_MAX_WEIGHT.equals(totalDroneWeight)){
                    loadedDrone.setDroneState(LOADED);
                }else{
                    loadedDrone.setDroneState(LOADING);
                }

                droneRepository.save(loadedDrone);

                List<Medication> medicationList = new ArrayList<>();
                ModelMapper modelMapper = new ModelMapper();
                loadDroneRequest.getLoadingMedicationList().forEach(medicationItem -> medicationList.add(modelMapper.map(medicationItem, Medication.class)));


                loadDroneRepository.save(LoadDrone.builder()
                        .loadingDroneSerialNumber(drone.get().getDroneSerialNumber())
                        .loadingDeliveryAddress(loadDroneRequest.getLoadingDeliveryAddress())
                        .loadingMedicationList(medicationList)
                        .loadingTotalQuantity(loadDroneRequest.getLoadingTotalQuantity())
                        .loadingDrone(loadedDrone)
                        .build());

                responseDTO= ResponseDTO.builder()
                        .status(OK)
                        .message(LOADED_SUCCESSFULLY)
                        .data(loadDroneRequest)
                        .build();
            }else{
                log.info("Preventing loading Drone with conditional checking");
                throw  new RuntimeException(LOAD_DRONE_ERROR);
            }

        }else{
                log.info("No Drone found in the database");
                throw new RuntimeException(NO_DATA_FOUND_IN_DB);
        }
        log.info("Response of loadDroneWithMedication: {}",responseDTO);
        log.info("Ending DroneServiceImpl -> loadingDroneWithMedication");
        return responseDTO;
    }

    /**Input - droneSerialNumber
     * Check loaded mediation for a given Drone (serial_number of Drone)
     * Since there can be same medication item loaded many times to a same drone, here it will consider unique medication items for a given drone only.
     * Return - ResponseDTO
     * **/
    @Override
    public ResponseDTO checkLoadedMedications(String droneSerialNumber) {
        log.info("Starting DroneServiceImpl -> checkingLoadedMedications");
        Optional<List<LoadDrone>> loadDrone = loadDroneRepository.findByLoadingDroneSerialNumber(droneSerialNumber);
        if(loadDrone.isPresent()){
            Set<Medication> set = new HashSet<>();
            loadDrone.get().forEach(i -> i.getLoadingMedicationList().forEach(m -> set.add(m)));
            responseDTO= ResponseDTO.builder()
                    .status(OK)
                    .message(CHECKED_LOADED_MEDICATIONS_SUCCESSFULLY)
                    .data(set)
                    .build();
        }else{
            throw new RuntimeException(NO_DATA_FOUND_IN_DB);
        }
        log.info("Response of checkLoadedMedications: {}",responseDTO);
        log.info("Ending DroneServiceImpl -> checkingLoadedMedications");
        return responseDTO;
    }

    /**Input - NA
     * Find all the drones in the database which are in State IDLE or LOADING only.
     * Return - ResponseDTO
     * **/
    @Override
    public ResponseDTO checkAvailableDrones() {
        log.info("Starting DroneServiceImpl -> checkAvailableDrones");
        List<DroneState> stateList = new ArrayList<>();
        stateList.add(IDLE);
        stateList.add(LOADING);

        Optional<List<Drone>>  drones = droneRepository.findAllByDroneStateIn(stateList);
        if(drones.isPresent()){
            responseDTO= ResponseDTO.builder()
                    .status(OK)
                    .message(AVAILABLE_DRONES_FOR_LOADIND)
                    .data(drones)
                    .build();
        }else{
            throw new RuntimeException(NO_DATA_FOUND_IN_DB);
        }
        log.info("Response of checkAvailableDrones: {}",responseDTO);
        log.info("Ending DroneServiceImpl -> checkAvailableDrones");
        return responseDTO;
    }

    /**Input - droneSerialNumber
     * Check the battery level of each drone in the database
     * Return - ResponseDTO
     * **/
    @Override
    public ResponseDTO checkBatteryLevel(String droneSerialNumber) {
        log.info("Starting DroneServiceImpl -> checkBatteryLevel");
        Optional<Drone> drone = droneRepository.findByDroneSerialNumber(droneSerialNumber);
        if(drone.isPresent()){
            responseDTO= ResponseDTO.builder()
                    .status(OK)
                    .message(CHECKED_LOADED_MEDICATIONS_SUCCESSFULLY)
                    .data(drone.get().getDroneBatteryCapacity())
                    .build();
        }else{
            throw new RuntimeException(NO_DATA_FOUND_IN_DB);
        }
        log.info("Response of checkBatteryLevel: {}",responseDTO);
        log.info("Ending DroneServiceImpl -> checkBatteryLevel");
        return responseDTO;
    }

    /**Input - List of MedicationDTO
     * Calculate the total weight of medication items
     * Return - Total Weight
     * **/
    private Double getTotalMedicationWeight(List<MedicationDTO> loadingMedicationList) {
        Double totalWeight=0.00;
        for(MedicationDTO medicationItem : loadingMedicationList){
            Optional<Medication> response = medicationRepository.findByMedicationCode(medicationItem.getMedicationCode());
            if(!response.isPresent()){
                throw new RuntimeException(NO_DATA_FOUND_IN_DB);
            }else{
                totalWeight= totalWeight+medicationItem.getMedicationWeight();
            }
        }
        return totalWeight;
    }

}
