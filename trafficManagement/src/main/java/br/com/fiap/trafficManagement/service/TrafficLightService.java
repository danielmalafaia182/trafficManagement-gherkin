package br.com.fiap.trafficManagement.service;

import br.com.fiap.trafficManagement.dto.ReturnMessageDto;
import br.com.fiap.trafficManagement.dto.TrafficLightExibhitionDto;
import br.com.fiap.trafficManagement.dto.TrafficLightInsertDto;
import br.com.fiap.trafficManagement.exception.BadRequestException;
import br.com.fiap.trafficManagement.exception.NotFoundException;
import br.com.fiap.trafficManagement.model.Location;
import br.com.fiap.trafficManagement.model.TrafficLight;
import br.com.fiap.trafficManagement.repository.TrafficLightRepository;
import br.com.fiap.trafficManagement.repository.TrafficSensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TrafficLightService {

    @Autowired
    private TrafficLightRepository trafficLightRepository;

    @Autowired
    private TrafficSensorRepository trafficSensorRepository;

    @Autowired
    private SchedulerService schedulerService;

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

        if (trafficLight.isCurrentStatus()) {
            throw new BadRequestException("Traffic Light already ON!");
        }
        trafficLight.setCurrentStatus(true);
        trafficLight.setFaultStatus(false);
        trafficLightRepository.save(trafficLight);

        schedulerService.scheduleTrafficLightColorChange(trafficLight);
    }

    public ReturnMessageDto reportFault(Long id) {
        TrafficLight trafficLight = trafficLightRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Traffic Light ID not found!"));

        if (trafficLight.isFaultStatus()) {
            throw new BadRequestException("Traffic Light already off!");
        } else {
            trafficLight.setCurrentStatus(false);
            trafficLight.setFaultStatus(true);
        }
        trafficLightRepository.save(trafficLight);
        return new ReturnMessageDto("Traffic light switched off, maintenance team asked for location: " + trafficLight.getLocation().getLatitude() + " , " + trafficLight.getLocation().getLongitude());
    }

    public ReturnMessageDto toggleEmergencyMode(Long id) {
        TrafficLight trafficLight = trafficLightRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Traffic Light ID not found!"));

        if (!trafficLight.isCurrentStatus()) {
            throw new BadRequestException("Traffic Light is currently turned off, cannot change to Emergency Mode!");
        }
        if (trafficLight.isPedestrianMode()) {
            throw new BadRequestException("Traffic Light in Pedestrian Mode!");
        }
        if (trafficLight.isEmergencyMode()) {
            throw new BadRequestException("Traffic Light already in Emergency Mode!");
        }

        trafficLight.setEmergencyMode(true);
        schedulerService.scheduleEmergencyMode(trafficLight);
        trafficLightRepository.save(trafficLight);
        return new ReturnMessageDto("Emergency Mode turned on for 3 minutes.");
    }


    public ReturnMessageDto togglePedestrianMode(Long id) {
        TrafficLight trafficLight = trafficLightRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Traffic Light ID not found!"));

        if (!trafficLight.isCurrentStatus()) {
            throw new BadRequestException("Traffic Light is currently turned off, cannot change to Pedestrian Mode!");
        }
        if (trafficLight.isEmergencyMode()) {
            throw new BadRequestException("Traffic Light in Emergency Mode!");
        }
        if (trafficLight.isPedestrianMode()) {
            throw new BadRequestException("Traffic Light already in Pedestrian Mode!");
        }
        trafficLight.setPedestrianMode(true);
        schedulerService.schedulePedestrianMode(trafficLight);
        trafficLightRepository.save(trafficLight);
        return new ReturnMessageDto("Pedestrian Mode turned on for 5 minutes.");
    }
}
