package ai.aitia.demo.car_common.dto;

import java.io.Serializable;

public class DoorStatusSensorReadDTO implements Serializable {

    public DoorStatusSensorReadDTO(String status) {
        this.status = status;
    }

    public DoorStatusSensorReadDTO() {
    }

    private String status;

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
