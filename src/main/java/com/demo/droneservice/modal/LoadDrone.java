package com.demo.droneservice.modal;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    @Id
    @Column(name="loading_total_quantity", columnDefinition = "VARCHAR(100) NOT NULL")
    private Integer loadingTotalQuantity;

    @Id
    @Column(name="loading_delivery_address", columnDefinition = "VARCHAR(100) NOT NULL")
    private String loadingDeliveryAddress;

    @Id
    @Column(name="loading_drone_serialNumber", columnDefinition = "VARCHAR(100) NOT NULL")
    private String loadingDroneSerialNumber;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Medication> loadingMedicationList;

}
