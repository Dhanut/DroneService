package com.demo.droneservice.controller;

import com.demo.droneservice.dto.request.DroneRegisterDTO;
import com.demo.droneservice.dto.request.LoadDroneDTO;
import com.demo.droneservice.dto.request.MedicationDTO;
import com.demo.droneservice.dto.response.ResponseDTO;
import com.demo.droneservice.exception.CustomBusinessException;
import com.demo.droneservice.service.DroneServiceImpl;
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

import static org.springframework.http.HttpStatus.CREATED;

@RunWith(MockitoJUnitRunner.class)
public class DroneControllerTest {

    @InjectMocks
    private DroneController droneController;

    @Mock
    private DroneServiceImpl droneService;

     ResponseDTO responseDTO = ResponseDTO.builder()
                                         .status("STATUS")
                                         .message("MESSAGE")
                                         .data("DATA")
                                         .build();

     @Test
     public void testRegisterDrone(){
     DroneRegisterDTO mockDroneRegisterDTO = DroneRegisterDTO.builder()
                                                             .droneState(DroneState.IDLE)
                                                             .droneModel(DroneModel.LIGHTWEIGHT)
                                                             .droneWeight(23.00)
                                                             .droneBatteryCapacity(10)
                                                             .droneSerialNumber("AB999000").build();
     Mockito.when(droneService.registerDrone(mockDroneRegisterDTO)).thenReturn(responseDTO);
     Assert.assertEquals(CREATED,droneController.registerDrone(mockDroneRegisterDTO).getStatusCode());
     }

    @Test
    public void testLoadDroneWithMedication() throws CustomBusinessException {

        List<MedicationDTO> mockMedicationList = new ArrayList<>();
        mockMedicationList.add(MedicationDTO.builder().medicationCode("1").medicationImage("xx").medicationName("xx").medicationWeight(1.5).build());
        mockMedicationList.add(MedicationDTO.builder().medicationCode("2").medicationImage("yy").medicationName("yy").medicationWeight(45.5).build());

        LoadDroneDTO mockLoadDroneDTO = LoadDroneDTO.builder()
                .loadingMedicationList(mockMedicationList).loadingDeliveryAddress("address").loadingTotalQuantity(2).loadingDroneSerialNumber("1111").build();
        Mockito.when(droneService.loadDroneWithMedication(mockLoadDroneDTO)).thenReturn(responseDTO);
        Assert.assertEquals(CREATED,droneController.loadDroneWithMedication(mockLoadDroneDTO).getStatusCode());
    }


    @Test
    public void testCheckLoadedMedications() throws CustomBusinessException {

        Mockito.when(droneService.checkLoadedMedications(ArgumentMatchers.anyString())).thenReturn(responseDTO);
        Assert.assertEquals(HttpStatus.OK,droneController.checkLoadedMedications("mockSerialNumber").getStatusCode());
     }

    @Test
    public void testCheckAvailableDrones() throws CustomBusinessException {
        Mockito.when(droneService.checkAvailableDrones()).thenReturn(responseDTO);
        Assert.assertEquals(HttpStatus.OK,droneController.checkAvailableDrones().getStatusCode());
     }

    @Test
    public void testCheckBatteryLevel() throws CustomBusinessException {
        Mockito.when(droneService.checkBatteryLevel(ArgumentMatchers.anyString())).thenReturn(responseDTO);
        Assert.assertEquals(HttpStatus.OK,droneController.checkBatteryLevel("mockSerialNumber").getStatusCode());
    }
}
