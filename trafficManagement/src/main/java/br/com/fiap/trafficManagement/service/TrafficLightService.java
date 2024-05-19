package br.com.fiap.trafficManagement.service;

import br.com.fiap.trafficManagement.dto.TrafficLightExibhitionDto;
import br.com.fiap.trafficManagement.dto.TrafficLightInsertDto;
import br.com.fiap.trafficManagement.dto.ReturnMessageDto;
import br.com.fiap.trafficManagement.exception.TrafficLightBadRequestException;
import br.com.fiap.trafficManagement.exception.NotFoundException;
import br.com.fiap.trafficManagement.model.Location;
import br.com.fiap.trafficManagement.model.TrafficLight;
import br.com.fiap.trafficManagement.model.TrafficLightColor;
import br.com.fiap.trafficManagement.repository.TrafficLightRepository;
import br.com.fiap.trafficManagement.utilitiesFunctions.TrafficLightCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TrafficLightService {

    @Autowired
    private TrafficLightRepository trafficLightRepository;

    public TrafficLightExibhitionDto insertTrafficLight(TrafficLightInsertDto trafficLightInsertDto) {
        TrafficLight trafficLight = new TrafficLight();
        Location location = new Location(trafficLightInsertDto.latitude(), trafficLightInsertDto.longitude());
        trafficLight.setLocation(location);

        trafficLightRepository.save(trafficLight);
        return new TrafficLightExibhitionDto(trafficLight);
    }

    public TrafficLightExibhitionDto queryById(Long id) {
        Optional<TrafficLight> trafficLightOptional = trafficLightRepository.findById(id);
        if (trafficLightOptional.isPresent()) {
            return new TrafficLightExibhitionDto(trafficLightOptional.get());
        } else {
            throw new NotFoundException("Traffic Light ID not found!");
        }
    }

    public Page<TrafficLightExibhitionDto> queryAllTrafficLights(Pageable page) {
        return trafficLightRepository
                .findAll(page)
                .map(TrafficLightExibhitionDto::new);
    }

    public void deleteTrafficLight(Long id) {
        Optional<TrafficLight> trafficLightOptional = trafficLightRepository.findById(id);
        if (trafficLightOptional.isPresent()) {
            trafficLightRepository.delete(trafficLightOptional.get());
        } else {
            throw new RuntimeException("Traffic Light ID not found!");
        }
    }

    public void activateTrafficLight(Long id) {
        TrafficLight trafficLight = trafficLightRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Traffic Light ID not found!"));

        if (trafficLight.isCurrentStatus()){
            throw new TrafficLightBadRequestException("Traffic Light already ON!");
        }

        // Ajustes baseados no tráfego e no clima
        int trafficAdjustment = TrafficLightCalculator.timerBasedOnTraffic(trafficLight.getTrafficDensity());
        int weatherAdjustment = TrafficLightCalculator.timerBasedOnWeather(trafficLight.getWeatherCondition());

        // Determinar o próximo estado e ajustar o timer
        switch (trafficLight.getCurrentColor()) {
            case RED:
                trafficLight.setCurrentColor(TrafficLightColor.GREEN);
                trafficLight.setLightTimer(TrafficLightCalculator.calculateBaseTime(TrafficLightColor.GREEN) + trafficAdjustment + weatherAdjustment);
                break;
            case GREEN:
                trafficLight.setCurrentColor(TrafficLightColor.YELLOW);
                trafficLight.setLightTimer(TrafficLightCalculator.calculateBaseTime(TrafficLightColor.YELLOW) + trafficAdjustment + weatherAdjustment);
                break;
            case YELLOW:
                trafficLight.setCurrentColor(TrafficLightColor.RED);
                trafficLight.setLightTimer(TrafficLightCalculator.calculateBaseTime(TrafficLightColor.RED) + trafficAdjustment + weatherAdjustment);
                break;
        }
        trafficLight.setCurrentStatus(true);
        trafficLight.setFaultStatus(false);
        trafficLightRepository.save(trafficLight);
    }

    public ReturnMessageDto manageEmergencyMode(Long id) {
        TrafficLight trafficLight = trafficLightRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Traffic Light ID not found!"));

        if (!trafficLight.isCurrentStatus()) {
            throw new TrafficLightBadRequestException("Traffic Light is currently turned off, cannot change to Emergency Mode!");
        } else if (trafficLight.isPedestrianMode()) {
            throw new TrafficLightBadRequestException("Traffic Light in Pedestrian Mode!");
        } else if (trafficLight.isEmergencyMode()) {
            throw new TrafficLightBadRequestException("Traffic Light already in Emergency Mode!");
        } else {
            trafficLight.setCurrentColor(TrafficLightColor.GREEN);
            trafficLight.setLightTimer(180);
            trafficLight.setEmergencyMode(true);
        }
        trafficLightRepository.save(trafficLight);
        return new ReturnMessageDto("Emergency Mode turned on for 3 minutes.");
    }

    public ReturnMessageDto managePedestrianMode(Long id) {
        TrafficLight trafficLight = trafficLightRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Traffic Light ID not found!"));

        if (!trafficLight.isCurrentStatus()) {
            throw new TrafficLightBadRequestException("Traffic Light is currently turned off, cannot change to Pedestrian Mode!");
        } else if (trafficLight.isEmergencyMode()) {
            throw new TrafficLightBadRequestException("Traffic Light in Emergency Mode!");
        } else if (trafficLight.isPedestrianMode()) {
            throw new TrafficLightBadRequestException("Traffic Light already in Pedestrian Mode!");
        } else {
            trafficLight.setCurrentColor(TrafficLightColor.RED);
            trafficLight.setLightTimer(300);
            trafficLight.setPedestrianMode(true);
        }
        trafficLightRepository.save(trafficLight);
        return new ReturnMessageDto("Pedestrian Mode turned on for 5 minutes.");
    }

    public ReturnMessageDto reportFault(Long id) {
        TrafficLight trafficLight = trafficLightRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Traffic Light ID not found!"));

        if (trafficLight.isFaultStatus()) {
            throw new TrafficLightBadRequestException("Traffic Light already off!");
        } else {
            trafficLight.setCurrentStatus(false);
            trafficLight.setFaultStatus(true);
        }
        trafficLightRepository.save(trafficLight);
        return new ReturnMessageDto("Traffic light switched off, maintenance team asked for location: " + trafficLight.getLocation().getLatitude() + " , " + trafficLight.getLocation().getLongitude());
    }

}