package com.demo.droneservice.modal;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "medications")
public class Medication implements Serializable {
    @Id
    @Column(name="medication_code", columnDefinition = "VARCHAR(100) NOT NULL")
    private String medicationCode;

    @Column(name="medication_name", columnDefinition = "VARCHAR(100) NOT NULL")
    private String medicationName;

    @Column(name="medication_weight")
    @DecimalMax(value = "500", message =" More than {value} grams")
    private Double medicationWeight;

    @Column(name="medication_image", columnDefinition = "VARCHAR(100) NOT NULL")
    private String medicationImage;
}
