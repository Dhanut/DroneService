package com.demo.droneservice.modal;

import com.demo.droneservice.util.DroneModel;
import com.demo.droneservice.util.DroneState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Max;
import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "drones")
public class Drone implements Serializable {
    @Id
    @Column(name="drone_serial_number", columnDefinition = "VARCHAR(100) NOT NULL")
    private String droneSerialNumber;

    @Column(name="drone_model")
    @Enumerated(value = EnumType.STRING)
    private DroneModel droneModel;

    @Column(name="drone_weight")
    @DecimalMax(value = "500", message =" More than {value} grams")
    private Double droneWeight;

    @Column(name="drone_battery_capacity")
    @Max(value=100,message="More than 100%")
    private Integer droneBatteryCapacity;

    @Column(name = "drone_state")
    @Enumerated(value = EnumType.STRING)
    private DroneState droneState;
}
