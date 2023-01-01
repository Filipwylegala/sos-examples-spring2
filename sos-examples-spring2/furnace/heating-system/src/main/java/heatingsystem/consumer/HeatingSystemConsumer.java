package heatingsystem.consumer;

import ai.aitia.arrowhead.application.library.ArrowheadService;

import ai.aitia.demo.car_common.dto.*;
import eu.arrowhead.common.SSLProperties;
import eu.arrowhead.common.dto.shared.*;

import heatingsystem.DoorStatusSensorsLastInfo;
import heatingsystem.HeatingSensorsLastInfo;
import heatingsystem.provider.controller.Furnace;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jose4j.json.internal.json_simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import heatingsystem.provider.controller.Thermostat;

import org.springframework.stereotype.Component;


import java.io.IOException;

import static heatingsystem.consumer.HeatingSystemConsumerConstants.READ_SENSOR_DOOR_STATUS;
import static heatingsystem.consumer.HeatingSystemConsumerConstants.READ_SENSOR_TEMPERATURE;

@Component
public class HeatingSystemConsumer {

    @Autowired
	private ArrowheadService arrowheadService;
    
    @Autowired
	protected SSLProperties sslProperties;
    
    private final Logger logger = LogManager.getLogger(HeatingSystemConsumer.class);

	public void run() {
		Thread thread = new Thread(() -> {
			while(true) {
				try {
					logger.info("Heating system consumes temperature from sensors");
					OrchestrationResponseDTO temperatureSensorResponse = ArrowheadOrchestrator.orchestrate(arrowheadService, READ_SENSOR_TEMPERATURE, logger, sslProperties);
					ArrowheadConsumer.consume(temperatureSensorResponse, arrowheadService, READ_SENSOR_TEMPERATURE, this::consumeReadTemperatureFromSensors, TemperatureSensorReadDTO.class, logger, sslProperties);

					logger.info("Heating system consumes door status from sensors");
					OrchestrationResponseDTO doorStatusResponse = ArrowheadOrchestrator.orchestrate(arrowheadService, READ_SENSOR_DOOR_STATUS, logger, sslProperties);
					ArrowheadConsumer.consume(doorStatusResponse, arrowheadService, READ_SENSOR_DOOR_STATUS, this::consumeReadDoorStatusFromSensors, DoorStatusSensorReadDTO.class, logger, sslProperties);

					String status = EnvironmentReader.readField("furnace_status", "/../../furnace.json");
					Thermostat.expectedFinalTempInCelsius = Double.parseDouble(EnvironmentReader.readField("expected_final_temp_in_celsius", "/../../furnace.json"));
					if(status.equals("ON")) {
						Furnace.status = Furnace.Status.ON;
					} else if (status.equals("OFF")) {
						Furnace.status = Furnace.Status.OFF;
					}
					Thread.sleep(1000);
				} catch (InterruptedException | IOException | ParseException e) {
					throw new RuntimeException(e);
				}
			}
		});
		thread.start();
	}
	private void consumeReadTemperatureFromSensors(TemperatureSensorReadDTO temperatureSensorReadDTO) {
		HeatingSensorsLastInfo.temperatureReadFromSensorInCelsius = temperatureSensorReadDTO.getTemperatureInCelsius();
	}

	private void consumeReadDoorStatusFromSensors(DoorStatusSensorReadDTO doorStatusSensorReadDTO) {
		DoorStatusSensorsLastInfo.status = doorStatusSensorReadDTO.getStatus();
	}
}
