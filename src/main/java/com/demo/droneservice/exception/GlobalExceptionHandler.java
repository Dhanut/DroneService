package com.demo.droneservice.exception;

import com.demo.droneservice.dto.response.ResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import static com.demo.droneservice.util.DroneServiceConstants.FAILED;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Method to globally handle any RuntimeException
     * **/
    @ExceptionHandler(value = { RuntimeException.class })
    public ResponseEntity<ResponseDTO> invalidInputException(RuntimeException runtimeException) {

        logger.error("Invalid Input Exception: {}", runtimeException.getMessage());
        return new ResponseEntity<>(new ResponseDTO(FAILED,runtimeException.getMessage(),java.time.LocalDateTime.now()),
                HttpStatus.BAD_REQUEST);
    }

    /**
     * Method to globally handle any Unauthorized end point execution
     * **/
    @ExceptionHandler(value = { HttpClientErrorException.Unauthorized.class })
    public ResponseEntity<ResponseDTO> unauthorizedException(HttpClientErrorException.Unauthorized unauthorized) {

        logger.error("Unauthorized Exception: {}", unauthorized.getMessage());
        return new ResponseEntity<>(new ResponseDTO(FAILED,unauthorized.getMessage(),java.time.LocalDateTime.now()),
                HttpStatus.BAD_REQUEST);
    }

    /**
     * Method to globally handle any Exception
     * **/
    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<ResponseDTO> exception(Exception exception) {

        logger.error("Exception: {}", exception.getMessage());
        return new ResponseEntity<>(new ResponseDTO(FAILED,exception.getMessage(),java.time.LocalDateTime.now()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
