package br.com.fiap.trafficManagement.service;

import br.com.fiap.trafficManagement.dto.ReturnMessageDto;
import br.com.fiap.trafficManagement.dto.TrafficSensorExibhitionDto;
import br.com.fiap.trafficManagement.dto.TrafficSensorInsertDto;
import br.com.fiap.trafficManagement.exception.BadRequestException;
import br.com.fiap.trafficManagement.exception.NotFoundException;
import br.com.fiap.trafficManagement.model.Location;
import br.com.fiap.trafficManagement.model.TrafficLight;
import br.com.fiap.trafficManagement.model.TrafficSensor;
import br.com.fiap.trafficManagement.repository.TrafficLightRepository;
import br.com.fiap.trafficManagement.repository.TrafficSensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrafficSensorService {

    @Autowired
    private TrafficSensorRepository trafficSensorRepository;

    @Autowired
    private TrafficLightRepository trafficLightRepository;

    @Autowired
    private SchedulerService schedulerService;

    public TrafficSensorExibhitionDto insertTrafficSensor(TrafficSensorInsertDto trafficSensorInsertDto) {
        TrafficSensor trafficSensor = new TrafficSensor();
        Location location = new Location(trafficSensorInsertDto.latitude(), trafficSensorInsertDto.longitude());
        trafficSensor.setLocation(location);

        trafficSensorRepository.save(trafficSensor);
        return new TrafficSensorExibhitionDto(trafficSensor);
    }

    public TrafficSensorExibhitionDto queryById(Long id) {
        Optional<TrafficSensor> trafficSensorOptional = trafficSensorRepository.findById(id);
        if (trafficSensorOptional.isPresent()) {
            return new TrafficSensorExibhitionDto(trafficSensorOptional.get());
        } else {
            throw new NotFoundException("Traffic Sensor ID not found!");

        }
    }

    public Page<TrafficSensorExibhitionDto> queryAllTrafficSensors(Pageable page) {
        return trafficSensorRepository
                .findAll(page)
                .map(TrafficSensorExibhitionDto::new);
    }

    public void deleteTrafficSensor(Long id) {
        Optional<TrafficSensor> trafficSensorOptional = trafficSensorRepository.findById(id);
        if (trafficSensorOptional.isPresent()) {
            trafficSensorRepository.delete(trafficSensorOptional.get());
        } else {
            throw new RuntimeException("Traffic Sensor ID not found!");
        }
    }

    public void activateTrafficSensor(Long id) {

        TrafficSensor trafficSensor = trafficSensorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Traffic Sensor ID not found!"));

        if (trafficSensor.isCurrentStatus()) {
            throw new BadRequestException("Traffic Sensor already ON!");
        }
        trafficSensor.setCurrentStatus(true);
        trafficSensor.setFaultStatus(false);
        trafficSensorRepository.save(trafficSensor);
    }


    public ReturnMessageDto reportFault(Long id) {
        TrafficSensor trafficSensor = trafficSensorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Traffic Sensor ID not found!"));

        if (trafficSensor.isFaultStatus()) {
            throw new BadRequestException("Traffic Sensor already off!");
        } else {
            trafficSensor.setCurrentStatus(false);
            trafficSensor.setFaultStatus(true);
        }
        trafficSensorRepository.save(trafficSensor);
        return new ReturnMessageDto("Traffic Sensor switched off, maintenance team asked for location: " + trafficSensor.getLocation().getLatitude() + " , " + trafficSensor.getLocation().getLongitude());
    }

    public ReturnMessageDto adjustTrafficLightsBasedOnDensity(Long id) {
        TrafficSensor trafficSensor = trafficSensorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Traffic Sensor ID not found!"));

        int randomTrafficVolume = (int) (Math.random() * 100);
        trafficSensor.setTrafficDensity(randomTrafficVolume);
        trafficSensorRepository.save(trafficSensor);

        double maxLatitude = trafficSensor.getLocation().getLatitude() + 100;
        double minLatitude = trafficSensor.getLocation().getLatitude() - 100;
        double maxLongitude = trafficSensor.getLocation().getLongitude() + 100;
        double minLongitude = trafficSensor.getLocation().getLongitude() - 100;

        List<TrafficLight> affectedLights = trafficLightRepository.findInLocationRange(minLatitude, maxLatitude, minLongitude, maxLongitude);

        for (TrafficLight trafficLight : affectedLights) {
            if (trafficSensor.getTrafficDensity() > 50) {
                schedulerService.scheduleAdjustedTrafficLightColorChange(trafficLight, 0.5);
            } else if (trafficSensor.getTrafficDensity() < 20) {
                schedulerService.scheduleAdjustedTrafficLightColorChange(trafficLight, 2.0);
            } else {
                schedulerService.scheduleTrafficLightColorChange(trafficLight);
            }
        }
        return new ReturnMessageDto("TrafficLights speed on range adjusted based on density");
    }

}
