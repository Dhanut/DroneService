package com.demo.droneservice.modal;

import com.demo.droneservice.util.DroneModel;
import com.demo.droneservice.util.DroneState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "drones")
public class Drone {
    @Id
    @Column(name="serial_number", length = 100)
    private String serialNumber;

    @Column(name="model")
    @Enumerated(value = EnumType.STRING)
    private DroneModel droneModel;

    @Column(name="weight")
    @DecimalMax(value = "500", message =" More than {value} grams")
    private Double weight;

    @Column(name="battery_capacity")
    @Max(value=100,message="More than 100%")
    private BigDecimal batteryCapacity;

    @Column(name = "drone_state")
    @Enumerated(value = EnumType.STRING)
    private DroneState droneState;
}
