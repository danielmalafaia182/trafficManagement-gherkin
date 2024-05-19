package br.com.fiap.trafficManagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TrafficLightBadRequestException extends RuntimeException {
    public TrafficLightBadRequestException(String message) {
        super(message);
    }

}
