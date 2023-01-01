package doorsensor.provider.controller;

import ai.aitia.demo.car_common.dto.DoorStatusSensorReadDTO;
import ai.aitia.demo.car_common.dto.EnvironmentReader;
import org.jose4j.json.internal.json_simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static doorsensor.provider.DoorSystemProviderConstants.SENSOR_DOOR;

@RestController
@RequestMapping(SENSOR_DOOR)
public class DoorSensorProviderController {

    Logger logger = LoggerFactory.getLogger(DoorSensorProviderController.class);

    @GetMapping
    public DoorStatusSensorReadDTO readDoorStatus() throws IOException, ParseException {
        logger.info("Door status read is requested.");
        String doorStatus = EnvironmentReader.readField("door_status", "/../../furnace.json");
        logger.info("The sensor read that door status is: {}", doorStatus);
        logger.info("Publishing door status to consumers...");
        return new DoorStatusSensorReadDTO(doorStatus);
    }
}
