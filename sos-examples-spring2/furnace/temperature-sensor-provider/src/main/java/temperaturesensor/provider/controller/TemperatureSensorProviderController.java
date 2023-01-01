package temperaturesensor.provider.controller;

import ai.aitia.demo.car_common.dto.EnvironmentReader;
import ai.aitia.demo.car_common.dto.TemperatureSensorReadDTO;
import org.jose4j.json.internal.json_simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static temperaturesensor.provider.TemperatureSystemProviderConstants.SENSOR_TEMPERATURE;

@RestController
@RequestMapping(SENSOR_TEMPERATURE)
public class TemperatureSensorProviderController {

    Logger logger = LoggerFactory.getLogger(TemperatureSensorProviderController.class);

    @GetMapping
    public TemperatureSensorReadDTO readTemperature() throws IOException, ParseException {
        logger.info("Temperature read is requested.");
        Double temperature = Double.parseDouble(EnvironmentReader.readField("fur_temperature_in_celsius"));
        logger.info("The sensor has measured temperature: {} in celsius.", temperature);
        logger.info("Publishing temperature measurement to consumers...");
        return new TemperatureSensorReadDTO(temperature);
    }
}
