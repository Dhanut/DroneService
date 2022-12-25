package com.demo.droneservice.service;

import com.demo.droneservice.dto.request.DroneRegisterDTO;
import com.demo.droneservice.dto.request.LoadDroneDTO;
import com.demo.droneservice.dto.request.MedicationDTO;
import com.demo.droneservice.exception.CustomBusinessException;
import com.demo.droneservice.modal.Drone;
import com.demo.droneservice.modal.LoadDrone;
import com.demo.droneservice.modal.Medication;
import com.demo.droneservice.repository.DroneRepository;
import com.demo.droneservice.repository.LoadDroneRepository;
import com.demo.droneservice.repository.MedicationRepository;
import com.demo.droneservice.util.DroneModel;
import com.demo.droneservice.util.DroneState;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.demo.droneservice.util.DroneModel.LIGHTWEIGHT;
import static com.demo.droneservice.util.DroneServiceConstants.NO_DATA_FOUND_IN_DB;
import static com.demo.droneservice.util.DroneState.IDLE;
import static com.demo.droneservice.util.DroneState.LOADED;
import static org.springframework.http.HttpStatus.CREATED;

@RunWith(MockitoJUnitRunner.class)
public class DroneServiceImplTest {

    @InjectMocks
    private DroneServiceImpl droneService;

    @Mock
    private DroneRepository droneRepository;

    @Mock
    private LoadDroneRepository loadDroneRepository;

    @Mock
    private MedicationRepository medicationRepository;

    @Test
    public void registerDrone(){
        DroneRegisterDTO mockDroneRegisterDTO = DroneRegisterDTO.builder()
                .droneState(IDLE)
                .droneModel(LIGHTWEIGHT)
                .droneWeight(23.00)
                .droneBatteryCapacity(10)
                .droneSerialNumber("AB999000").build();
        Assert.assertEquals("OK",droneService.registerDrone(mockDroneRegisterDTO).getStatus());

    }

    @Test
    public void testLoadDroneWithMedication() throws CustomBusinessException {

        List<MedicationDTO> mockMedicationList = new ArrayList<>();
        mockMedicationList.add(MedicationDTO.builder().medicationCode("1").medicationImage("xx").medicationName("xx").medicationWeight(1.5).build());
        mockMedicationList.add(MedicationDTO.builder().medicationCode("2").medicationImage("yy").medicationName("yy").medicationWeight(45.5).build());

        LoadDroneDTO mockLoadDroneDTO = LoadDroneDTO.builder()
                .loadingDroneSerialNumber("AB999000")
                .loadingTotalQuantity(2)
                .loadingDeliveryAddress("address")
                .loadingMedicationList(mockMedicationList).build();
        Drone mockDrone = new Drone("AB999000",LIGHTWEIGHT,200.00,45,IDLE);
        Medication mockMedication = new Medication("1","xx",1.5,"xx");
        Mockito.when(droneRepository.findByDroneSerialNumber(mockLoadDroneDTO.getLoadingDroneSerialNumber())).thenReturn(Optional.of(mockDrone));
        Mockito.when(medicationRepository.findByMedicationCode(ArgumentMatchers.anyString())).thenReturn(Optional.of(mockMedication));

        Assert.assertEquals("OK",droneService.loadDroneWithMedication(mockLoadDroneDTO).getStatus());
    }

    @Test
    public void testLoadDroneWithMedicationWithNoData() throws CustomBusinessException {
        List<MedicationDTO> mockMedicationList = new ArrayList<>();
        mockMedicationList.add(MedicationDTO.builder().medicationCode("1").medicationImage("xx").medicationName("xx").medicationWeight(50.00).build());
        mockMedicationList.add(MedicationDTO.builder().medicationCode("2").medicationImage("yy").medicationName("yy").medicationWeight(50.00).build());

        LoadDroneDTO mockLoadDroneDTO = LoadDroneDTO.builder()
                .loadingDroneSerialNumber("AB999000")
                .loadingTotalQuantity(2)
                .loadingDeliveryAddress("address")
                .loadingMedicationList(mockMedicationList).build();
        Drone mockDrone = new Drone("AB999000",LIGHTWEIGHT,400.00,45,IDLE);
        Medication mockMedication = new Medication("1","xx",1.5,"xx");
        Mockito.when(droneRepository.findByDroneSerialNumber(mockLoadDroneDTO.getLoadingDroneSerialNumber())).thenReturn(Optional.of(mockDrone));
        Mockito.when(medicationRepository.findByMedicationCode(ArgumentMatchers.anyString())).thenReturn(Optional.of(mockMedication));

        Assert.assertEquals("OK",droneService.loadDroneWithMedication(mockLoadDroneDTO).getStatus());
    }

    @Test(expected = CustomBusinessException.class)
    public void testLoadDroneWithMedicationWithValidationError() throws CustomBusinessException {
        List<MedicationDTO> mockMedicationList = new ArrayList<>();
        mockMedicationList.add(MedicationDTO.builder().medicationCode("1").medicationImage("xx").medicationName("xx").medicationWeight(50.00).build());
        mockMedicationList.add(MedicationDTO.builder().medicationCode("2").medicationImage("yy").medicationName("yy").medicationWeight(50.00).build());

        LoadDroneDTO mockLoadDroneDTO = LoadDroneDTO.builder()
                .loadingDroneSerialNumber("AB999000")
                .loadingTotalQuantity(2)
                .loadingDeliveryAddress("address")
                .loadingMedicationList(mockMedicationList).build();
        Drone mockDrone = new Drone("AB999000",LIGHTWEIGHT,400.00,70,LOADED);
        Medication mockMedication = new Medication("1","xx",1.5,"xx");
        Mockito.when(droneRepository.findByDroneSerialNumber(mockLoadDroneDTO.getLoadingDroneSerialNumber())).thenReturn(Optional.of(mockDrone));
        Mockito.when(medicationRepository.findByMedicationCode(ArgumentMatchers.anyString())).thenReturn(Optional.of(mockMedication));

        Assert.assertEquals("Preventing loading Drone with conditional checking",droneService.loadDroneWithMedication(mockLoadDroneDTO).getMessage());
    }


   /** @Test(expected = RuntimeException.class)
    public void testLoadDroneWithMedicationWithoutDroneData() throws RuntimeException{
        List<MedicationDTO> mockMedicationList = new ArrayList<>();
        mockMedicationList.add(MedicationDTO.builder().medicationCode("1").medicationImage("xx").medicationName("xx").medicationWeight(50.00).build());
        mockMedicationList.add(MedicationDTO.builder().medicationCode("2").medicationImage("yy").medicationName("yy").medicationWeight(50.00).build());

        LoadDroneDTO mockLoadDroneDTO = LoadDroneDTO.builder()
                .loadingDroneSerialNumber("AB999000")
                .loadingTotalQuantity(2)
                .loadingDeliveryAddress("address")
                .loadingMedicationList(mockMedicationList).build();
        Drone mockDrone = new Drone("AB999000",LIGHTWEIGHT,400.00,70,LOADED);
        Medication mockMedication = new Medication("1","xx",1.5,"xx");
        Mockito.when(droneRepository.findByDroneSerialNumber(mockLoadDroneDTO.getLoadingDroneSerialNumber())).thenReturn(null);
        Mockito.when(medicationRepository.findByMedicationCode(ArgumentMatchers.anyString())).thenReturn(Optional.of(mockMedication));

        Assert.assertEquals("No data found in database",droneService.loadDroneWithMedication(mockLoadDroneDTO).getMessage());
    }**/

   @Test
   public void testCheckLoadedMedications() throws CustomBusinessException {

       List<Medication> mockMedicationList = new ArrayList<>();
       mockMedicationList.add(Medication.builder().medicationCode("1").medicationImage("xx").medicationName("xx").medicationWeight(1.5).build());
       mockMedicationList.add(Medication.builder().medicationCode("2").medicationImage("yy").medicationName("yy").medicationWeight(45.5).build());

       List<LoadDrone> loadDroneList = new ArrayList<>();
       loadDroneList.add(LoadDrone.builder().loadingMedicationList(mockMedicationList).loadingDeliveryAddress("address").loadingTotalQuantity(2).loadingDroneSerialNumber("1111").build());
       loadDroneList.add(LoadDrone.builder().loadingMedicationList(mockMedicationList).loadingDeliveryAddress("address").loadingTotalQuantity(2).loadingDroneSerialNumber("1111").build());

       Mockito.when(loadDroneRepository.findByLoadingDroneSerialNumber(ArgumentMatchers.anyString())).thenReturn(loadDroneList);
       Assert.assertEquals("OK",droneService.checkLoadedMedications("mockSerialNumber").getStatus());
   }

    @Test(expected = CustomBusinessException.class)
    public void testCheckLoadedMedicationsWithException() throws CustomBusinessException {
        List<LoadDrone> loadDroneList = new ArrayList<>();
        Mockito.when(loadDroneRepository.findByLoadingDroneSerialNumber(ArgumentMatchers.anyString())).thenReturn(loadDroneList);
        Assert.assertEquals(HttpStatus.BAD_REQUEST,droneService.checkLoadedMedications("mockSerialNumber").getStatus());
    }
    @Test
    public void testCheckAvailableDrones() throws CustomBusinessException {
        List<Drone> droneList = new ArrayList<>();
        droneList.add(Drone.builder().droneState(IDLE).droneModel(LIGHTWEIGHT).droneWeight(100.00).droneBatteryCapacity(45).droneSerialNumber("1111111111").build());
        droneList.add(Drone.builder().droneState(IDLE).droneModel(LIGHTWEIGHT).droneWeight(200.00).droneBatteryCapacity(65).droneSerialNumber("2222222222").build());
        Mockito.when(droneRepository.findAllByDroneStateIn(ArgumentMatchers.any())).thenReturn(droneList);
        Assert.assertEquals("OK",droneService.checkAvailableDrones().getStatus());
    }

    @Test(expected = CustomBusinessException.class)
    public void testCheckAvailableDronesWithEmptyList() throws CustomBusinessException {
        List<Drone> droneList = new ArrayList<>();
        Mockito.when(droneRepository.findAllByDroneStateIn(ArgumentMatchers.any())).thenReturn(droneList);
        Assert.assertEquals(HttpStatus.BAD_REQUEST,droneService.checkAvailableDrones().getStatus());
    }

    @Test
    public void testCheckBatteryLevel() throws CustomBusinessException {
        Drone mockDrone = new Drone("AB999000",LIGHTWEIGHT,200.00,45,IDLE);
        Mockito.when(droneRepository.findByDroneSerialNumber(ArgumentMatchers.anyString())).thenReturn(Optional.of(mockDrone));
        Assert.assertEquals("OK",droneService.checkBatteryLevel("xxxxxxxx").getStatus());

    }

    @Test(expected = CustomBusinessException.class)
    public void testCheckBatteryLevelWithError() throws CustomBusinessException {
        Drone mockDrone = new Drone("AB999000",LIGHTWEIGHT,200.00,45,IDLE);
        Mockito.when(droneService.checkBatteryLevel(ArgumentMatchers.anyString())).thenThrow(new CustomBusinessException("MESSAGE"));
        Assert.assertEquals("No data found in database",droneService.checkBatteryLevel("xxxxxxxx").getMessage());

    }
}
