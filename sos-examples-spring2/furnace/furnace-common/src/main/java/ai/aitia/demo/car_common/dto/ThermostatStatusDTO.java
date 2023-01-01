package ai.aitia.demo.car_common.dto;

public class ThermostatStatusDTO {

    public ThermostatStatusDTO(String status) {
        this.status = status;
    }

    public ThermostatStatusDTO() {}
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
