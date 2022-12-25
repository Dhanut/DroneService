package schedular;

import com.demo.droneservice.modal.Drone;
import com.demo.droneservice.repository.DroneRepository;
import com.demo.droneservice.schedular.CheckBatteryLevel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;


import static com.demo.droneservice.util.DroneModel.LIGHTWEIGHT;
import static com.demo.droneservice.util.DroneState.IDLE;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CheckBatteryLevelTest {

    @InjectMocks
    private CheckBatteryLevel checkBatteryLevel;

    @Mock
    private DroneRepository droneRepository;

    @Test
    public void testCheckBatteryLevel(){
        CheckBatteryLevel mockCheckBatteryLevel = mock(CheckBatteryLevel.class);
        List<Drone> droneList = new ArrayList<>();
        droneList.add(Drone.builder().droneState(IDLE).droneModel(LIGHTWEIGHT).droneWeight(100.00).droneBatteryCapacity(45).droneSerialNumber("1111111111").build());
        droneList.add(Drone.builder().droneState(IDLE).droneModel(LIGHTWEIGHT).droneWeight(200.00).droneBatteryCapacity(65).droneSerialNumber("2222222222").build());
        //Mockito.when(droneRepository.findAll()).thenReturn(droneList);
        //doNothing().when(mockCheckBatteryLevel).checkBatteryLevel();

    }
}
