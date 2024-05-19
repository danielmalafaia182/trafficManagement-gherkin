package br.com.fiap.trafficManagement.dto;

import br.com.fiap.trafficManagement.model.Location;
import br.com.fiap.trafficManagement.model.TrafficLight;

public record TrafficLightExibhitionDto(
        Long trafficLightId,
        Location location,
        boolean currentStatus

) {
    public TrafficLightExibhitionDto(TrafficLight trafficLight) {
        this(
                trafficLight.getTrafficLightId(),
                trafficLight.getLocation(),
                trafficLight.isCurrentStatus()
        );
    }
}