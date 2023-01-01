package heatingsystem.provider.controller;


import heatingsystem.DoorStatusSensorsLastInfo;
import heatingsystem.HeatingSensorsLastInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


@RestController
public class HeatingSystemProviderController {

    Logger logger = LoggerFactory.getLogger(HeatingSystemProviderController.class);

    @PostMapping(path = "/thermostat/temperature/{celsius}")
    public void setTemperature(@Min(0) @Max(10000) @PathVariable Double celsius) {
        logger.info("Furnace heating system set temperature to {}C", celsius);
        Thermostat.expectedFinalTempInCelsius = celsius;
    }

    @GetMapping(path = "/thermostat/status")
    public Thermostat.Status getThermostatStatus()
    {
        logger.info("Thermostat status is requested from heating system.");
        logger.info("Furnace status is : {}", Furnace.status);
        logger.info("Door status is : {}", DoorStatusSensorsLastInfo.status);

        return Thermostat.getStatus(Furnace.status, DoorStatusSensorsLastInfo.status, HeatingSensorsLastInfo.temperatureReadFromSensorInCelsius);
    }
}
