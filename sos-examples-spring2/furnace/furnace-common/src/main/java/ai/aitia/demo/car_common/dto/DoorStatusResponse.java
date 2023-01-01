package ai.aitia.demo.car_common.dto;

import java.io.Serializable;

public class DoorStatusResponse implements Serializable {
    private final String status;

    public DoorStatusResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
