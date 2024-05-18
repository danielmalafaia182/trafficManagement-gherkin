package br.com.fiap.trafficManagement.dto;

import br.com.fiap.trafficManagement.model.Location;
import br.com.fiap.trafficManagement.model.TrafficLight;
import br.com.fiap.trafficManagement.model.TrafficLightColor;

public record TrafficLightExhibitionDto(
        Long trafficLightId,
        Location location,
        TrafficLightColor currentColor

) {
    public TrafficLightExhibitionDto(TrafficLight trafficLight) {
        this(
                trafficLight.getTrafficLightId(),
                trafficLight.getLocation(),
                trafficLight.getCurrentColor()
        );
    }
}