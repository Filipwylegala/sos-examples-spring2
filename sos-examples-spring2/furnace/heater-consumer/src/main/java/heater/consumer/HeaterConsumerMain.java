package heater.consumer;

import ai.aitia.demo.car_common.dto.*;
import eu.arrowhead.common.dto.shared.OrchestrationResponseDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jose4j.json.internal.json_simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import ai.aitia.arrowhead.application.library.ArrowheadService;
import eu.arrowhead.common.CommonConstants;
import eu.arrowhead.common.SSLProperties;

import java.io.IOException;

import static ai.aitia.demo.car_common.dto.EnvironmentReader.readField;
import static ai.aitia.demo.car_common.dto.EnvironmentWriter.writeDoubleField;
import static heater.consumer.HeaterConsumerConstants.GET_THERMOSTAT_STATUS;
import static java.lang.Double.parseDouble;

import static java.lang.Math.abs;

@SpringBootApplication
@ComponentScan(basePackages = {CommonConstants.BASE_PACKAGE, HeaterConsumerConstants.BASE_PACKAGE, ArrowheadPackage.PATH})
public class HeaterConsumerMain implements ApplicationRunner {
    
    //=================================================================================================
	// members
	private static double HEATER_TEMPERATURE_IN_CELSIUS = 1000d;
	private static double HEAT_EXCHANGE_COEFFICIENT = 0.002d;

	private static double EPSILON = 0.05d;
    @Autowired
	private ArrowheadService arrowheadService;
    
    @Autowired
	protected SSLProperties sslProperties;
    
    private final Logger logger = LogManager.getLogger(HeaterConsumerMain.class);
    
    //=================================================================================================
	// methods

	//------------------------------------------------------------------------------------------------
    public static void main( final String[] args ) {
    	SpringApplication.run(HeaterConsumerMain.class, args);
    }

    //-------------------------------------------------------------------------------------------------
    @Override
	public void run(final ApplicationArguments args) {
		Thread thread = new Thread(() -> {
			while(true) {
				try {
					OrchestrationResponseDTO response = ArrowheadOrchestrator.orchestrate(arrowheadService, GET_THERMOSTAT_STATUS, logger, sslProperties);
					ArrowheadConsumer.consume(response, arrowheadService, GET_THERMOSTAT_STATUS, this::consumeThermostatTemperature, ThermostatStatusDTO.class, logger, sslProperties);
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		});
		thread.start();
	}

	private void consumeThermostatTemperature(ThermostatStatusDTO thermostatStatusDTO) {
		try {
			double furnaceTempInCelsius = parseDouble(readField("fur_temperature_in_celsius"));
			double furnaceTempAfterHeatExchange;
			if (thermostatStatusDTO.getStatus().equals("HEAT_UP")) {
				furnaceTempAfterHeatExchange = calculateNextTempAfterHeatUp(furnaceTempInCelsius);
				logger.info("Heating up to {} celsius ...", furnaceTempAfterHeatExchange);
			} else {
				double roomTempInCelsius = parseDouble(readField("room_temperature_in_celsius"));
				furnaceTempAfterHeatExchange = calculateNextTempAfterCoolDown(furnaceTempInCelsius, roomTempInCelsius);
				logger.info("Cooling down to {} celsius ...", furnaceTempAfterHeatExchange);
			}
			writeDoubleField("fur_temperature_in_celsius", furnaceTempAfterHeatExchange);
		} catch (IOException | ParseException cause) {
			logger.error(cause.getMessage());
		}
	}

	private double calculateNextTempAfterHeatUp(double furnaceTempInCelsius) {
		double furnaceTempAfterHeatExchange;
		if (abs(furnaceTempInCelsius - HEATER_TEMPERATURE_IN_CELSIUS) < EPSILON) {
			furnaceTempAfterHeatExchange = HEATER_TEMPERATURE_IN_CELSIUS;
		} else {
			furnaceTempAfterHeatExchange = calculateNextTempAfterHeatExchange(furnaceTempInCelsius, HEATER_TEMPERATURE_IN_CELSIUS);
		}
		return furnaceTempAfterHeatExchange;
	}

	private double calculateNextTempAfterCoolDown(double furnaceTempInCelsius, double roomTempInCelsius) {
		double furnaceTempAfterHeatExchange;
		if (abs(furnaceTempInCelsius - roomTempInCelsius) < EPSILON) {
			furnaceTempAfterHeatExchange = roomTempInCelsius;
		} else {
			furnaceTempAfterHeatExchange = calculateNextTempAfterHeatExchange(furnaceTempInCelsius, roomTempInCelsius);
		}
		return furnaceTempAfterHeatExchange;
	}
	private double calculateNextTempAfterHeatExchange(double furnaceTempInCelsius, double expectedTemp) {
		return expectedTemp - (expectedTemp - furnaceTempInCelsius) * Math.pow(Math.E, -HEAT_EXCHANGE_COEFFICIENT);
	}
}
