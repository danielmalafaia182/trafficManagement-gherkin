package br.com.fiap.trafficManagement.dto;

import jakarta.validation.constraints.NotNull;

public record TrafficLightInsertDto(
        @NotNull(message = "must not be null!")
        double latitude,
        @NotNull(message = "must not be null!")
        double longitude
        //@NotBlank(message = "must not be null!")
        //String currentColor
) {
}
