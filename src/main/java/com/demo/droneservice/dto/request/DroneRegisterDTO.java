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
    @JsonProperty(required = true)
    private String serialNumber;

    @NotNull(message =" Serial Number must not be null")
    @JsonProperty(required = true)
    private DroneModel droneModel;

    @DecimalMax(value = "500", message =" More than {value} grams")
    @JsonProperty(required = true)
    private Double weight;

    @DecimalMax(value = "100" ,  message =" More than 100%")
    @JsonProperty(required = true)
    private BigDecimal batteryCapacity;

    @NotNull(message =" Serial Number must not be null")
    @JsonProperty(required = true)
    private DroneState droneState;

}
