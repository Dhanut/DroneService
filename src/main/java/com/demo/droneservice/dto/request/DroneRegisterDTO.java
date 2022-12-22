package com.demo.droneservice.dto.request;

import com.demo.droneservice.util.DroneModel;
import com.demo.droneservice.util.DroneState;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DroneRegisterDTO {
    @NotNull(message =" Serial Number must not be null")
    @NotBlank(message =" Serial Number must not be empty")
    @Size(min=5,max=100,message="Serial number must not be greater than {value} characters")
    @JsonProperty(required = true)
    private String droneSerialNumber;

    @NotNull(message =" Drone Model must not be null")
    @JsonProperty(required = true)
    private DroneModel droneModel;

    @DecimalMax(value = "500", message =" More than {value} grams")
    @JsonProperty(required = true)
    private Double droneWeight;

    @DecimalMax(value = "100" ,  message =" More than 100%")
    @JsonProperty(required = true)
    private BigDecimal droneBatteryCapacity;

    @NotNull(message =" Serial Number must not be null")
    @JsonProperty(required = true)
    private DroneState droneState;

}
