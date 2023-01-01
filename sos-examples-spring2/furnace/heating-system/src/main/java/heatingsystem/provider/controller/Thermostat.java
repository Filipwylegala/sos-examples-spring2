package heatingsystem.provider.controller;

import static heatingsystem.provider.controller.Furnace.Status.ON;
import static heatingsystem.provider.controller.Thermostat.Status.COOL_DOWN;
import static heatingsystem.provider.controller.Thermostat.Status.HEAT_UP;

public class Thermostat {
    public static Double expectedFinalTempInCelsius = 100.0d;


    static Status getStatus(Furnace.Status furnaceStatus, String doorStatus, Double heatingSensorsLastTemperature) {
        if (expectedFinalTempInCelsius > heatingSensorsLastTemperature && furnaceStatus == ON && doorStatus.equals("closed")) {
            return HEAT_UP;
        }
        return COOL_DOWN;
    }
    enum Status {
        COOL_DOWN, HEAT_UP
    }

}
