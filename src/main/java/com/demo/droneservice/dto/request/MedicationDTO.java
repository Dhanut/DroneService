package com.demo.droneservice.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicationDTO {

    @NotNull(message =" code must not be null")
    @NotBlank(message =" code must not be empty")
    @Pattern(regexp = "^[A-Z0-9.\\/_ ]*$", message="Allowed only uppercase letters, underscore and numbers")
    @JsonProperty(required = true)
    private String medicationCode;

    @NotNull(message =" medicationName must not be null")
    @NotBlank(message =" medicationName must not be empty")
    @JsonProperty(required = true)
    @Pattern(regexp = "^[a-zA-Z0-9.\\-\\/_ ]*$", message="Allowed only numbers, letters, underscore and dash characters")
    private String medicationName;

    @DecimalMax(value = "500", message =" More than {value} grams")
    @JsonProperty(required = true)
    private Double medicationWeight;

    @NotNull(message =" image must not be null")
    @NotBlank(message =" image must not be empty")
    @JsonProperty(required = true)
    private String medicationImage;

}
