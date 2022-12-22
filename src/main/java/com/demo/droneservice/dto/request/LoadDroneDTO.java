package com.demo.droneservice.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoadDroneDTO {
    @NotNull(message =" Serial Number must not be null")
    @NotBlank(message =" Serial Number must not be empty")
    @JsonProperty(required = true)
    private String loadingDroneSerialNumber;


    /** loadingTotalQuantity = No of items in the loadingMedicationList**/
    @NotNull(message="Total Quantity must not be null")
    @JsonProperty(required = true)
    private Integer loadingTotalQuantity;

    @NotNull(message =" deliveryAddress must not be null")
    @NotBlank(message =" deliveryAddress must not be empty")
    @JsonProperty(required = true)
    private String loadingDeliveryAddress;

    @NotNull(message =" medicationList must not be null")
    @JsonProperty(required = true)
    private List<MedicationDTO> loadingMedicationList;

}
