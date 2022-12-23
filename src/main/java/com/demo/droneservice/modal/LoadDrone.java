package com.demo.droneservice.modal;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "load_drone")
public class LoadDrone implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="loading_id")
    private Long loadingId;

    @Column(name="loading_total_quantity")
    private Integer loadingTotalQuantity;

    @Column(name="loading_delivery_address", columnDefinition = "VARCHAR(100) NOT NULL")
    private String loadingDeliveryAddress;

    @Column(name="loading_drone_serialNumber", columnDefinition = "VARCHAR(100) NOT NULL")
    private String loadingDroneSerialNumber;

    @ManyToMany
    private List<Medication> loadingMedicationList;

    @OneToOne
    @JoinColumn(name = "fk_drone_serial_number", referencedColumnName = "drone_serial_number")
    private Drone loadingDrone;

}
