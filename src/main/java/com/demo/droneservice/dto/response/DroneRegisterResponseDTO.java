package com.demo.droneservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DroneRegisterResponseDTO {

    private String status;
    private String message;
    private String serialNumber;
    private LocalDateTime timestamp;
}
