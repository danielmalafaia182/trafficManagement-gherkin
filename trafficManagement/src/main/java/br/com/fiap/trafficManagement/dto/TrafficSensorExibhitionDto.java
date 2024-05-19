package br.com.fiap.trafficManagement.dto;

import br.com.fiap.trafficManagement.model.Location;
import br.com.fiap.trafficManagement.model.TrafficSensor;

public record TrafficSensorExibhitionDto(
        Long trafficSensorId,
        Location location,
        boolean currentStatus
) {
    public TrafficSensorExibhitionDto(TrafficSensor trafficSensor){
        this(
                trafficSensor.getTrafficSensorId(),
                trafficSensor.getLocation(),
                trafficSensor.isCurrentStatus()
        );
    }
}
