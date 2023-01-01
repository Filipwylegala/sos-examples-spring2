package ai.aitia.demo.car_common.dto;

import java.io.Serializable;

public class TemperatureSensorReadDTO implements Serializable {

    public TemperatureSensorReadDTO(Double temperatureInCelsius) {
        this.temperatureInCelsius = temperatureInCelsius;
    }

    public TemperatureSensorReadDTO() {
    }

    private Double temperatureInCelsius;

    public void setTemperatureInCelsius(Double temperatureInCelsius) {
        this.temperatureInCelsius = temperatureInCelsius;
    }

    public Double getTemperatureInCelsius() {
        return temperatureInCelsius;
    }
}
