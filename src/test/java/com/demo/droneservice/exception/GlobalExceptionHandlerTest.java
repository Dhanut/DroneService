package com.demo.droneservice.exception;
import com.demo.droneservice.dto.response.ResponseDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@RunWith(MockitoJUnitRunner.class)
public class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    ResponseDTO responseDTO = ResponseDTO.builder()
            .status("failed")
            .message("MESSAGE")
            .data("DATA")
            .build();

    @Test(expected = RuntimeException.class)
    public void testInvalidInputException(){
        ResponseEntity<ResponseDTO> responseEntity = new ResponseEntity<>(responseDTO,HttpStatus.BAD_REQUEST);
        Mockito.when(globalExceptionHandler.invalidInputException(new RuntimeException())).thenReturn(responseEntity);
        Assert.assertEquals(HttpStatus.BAD_REQUEST,globalExceptionHandler.invalidInputException(new RuntimeException()).getStatusCode());
    }

    @Test(expected = Exception.class)
    public void testException(){
        ResponseEntity<ResponseDTO> responseEntity = new ResponseEntity<>(responseDTO,HttpStatus.BAD_REQUEST);
        Mockito.when(globalExceptionHandler.exception(new Exception())).thenReturn(responseEntity);
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,globalExceptionHandler.exception(new Exception()).getStatusCode());
    }
}
