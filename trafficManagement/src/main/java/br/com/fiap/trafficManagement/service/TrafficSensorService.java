package br.com.fiap.trafficManagement.service;

import br.com.fiap.trafficManagement.dto.TrafficSensorExibhitionDto;
import br.com.fiap.trafficManagement.dto.TrafficSensorInsertDto;
import br.com.fiap.trafficManagement.model.Location;
import br.com.fiap.trafficManagement.model.TrafficSensor;
import br.com.fiap.trafficManagement.repository.TrafficSensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TrafficSensorService {

    @Autowired
    private TrafficSensorRepository trafficSensorRepository;

    public TrafficSensorExibhitionDto insertTrafficSensor(TrafficSensorInsertDto trafficSensorInsertDto) {
        TrafficSensor trafficSensor = new TrafficSensor();
        Location location = new Location(trafficSensorInsertDto.latitude(), trafficSensorInsertDto.longitude());
        trafficSensor.setLocation(location);

        trafficSensorRepository.save(trafficSensor);
        return new TrafficSensorExibhitionDto(trafficSensor);
    }

    public TrafficSensorExibhitionDto queryById(Long id){
        Optional<TrafficSensor> trafficSensorOptional = trafficSensorRepository.findById(id);
        if (trafficSensorOptional.isPresent()){
            return new TrafficSensorExibhitionDto(trafficSensorOptional.get());
        } else {

        }
    }
    /*
    public TrafficLightExibhitionDto queryById(Long id) {
        Optional<TrafficLight> trafficLightOptional = trafficLightRepository.findById(id);
        if (trafficLightOptional.isPresent()) {
            return new TrafficLightExibhitionDto(trafficLightOptional.get());
        } else {
            throw new TrafficLightNotFoundException("Traffic Light ID not found!");
        }
    }
     */
}
