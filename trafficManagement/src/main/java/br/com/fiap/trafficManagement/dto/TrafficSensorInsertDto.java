package br.com.fiap.trafficManagement.dto;

import jakarta.validation.constraints.NotNull;

public record TrafficSensorInsertDto(
        @NotNull(message = "must not be null!")
        double latitude,
        @NotNull(message = "must not be null!")
        double longitude
) {

}
