package br.com.fiap.trafficManagement.service;

import br.com.fiap.trafficManagement.dto.TrafficLightExhibitionDto;
import br.com.fiap.trafficManagement.dto.TrafficLightInsertDto;
import br.com.fiap.trafficManagement.exception.TrafficLightNotFoundException;
import br.com.fiap.trafficManagement.model.TrafficLight;
import br.com.fiap.trafficManagement.model.TrafficLightColor;
import br.com.fiap.trafficManagement.repository.TrafficLightRepository;
import br.com.fiap.trafficManagement.utilitiesFunctions.TrafficLightCalculator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TrafficLightService {

    @Autowired
    private TrafficLightRepository trafficLightRepository;

    public TrafficLightExhibitionDto insertTrafficLight(TrafficLightInsertDto trafficLightInsertDto) {
        TrafficLight trafficLight = new TrafficLight();
        BeanUtils.copyProperties(trafficLightInsertDto, trafficLight);

        return new TrafficLightExhibitionDto(trafficLightRepository.save(trafficLight));
    }

    public TrafficLightExhibitionDto queryById(Long id) {
        Optional<TrafficLight> trafficLightOptional = trafficLightRepository.findById(id);
        if (trafficLightOptional.isPresent()) {
            return new TrafficLightExhibitionDto(trafficLightOptional.get());
        } else {
            throw new TrafficLightNotFoundException("Traffic Light ID not found!");
        }
    }

    public Page<TrafficLightExhibitionDto> queryAllTrafficLights(Pageable page) {
        return trafficLightRepository
                .findAll(page)
                .map(TrafficLightExhibitionDto::new);
    }

    public void deleteTrafficLight(Long id) {
        Optional<TrafficLight> trafficLightOptional = trafficLightRepository.findById(id);
        if (trafficLightOptional.isPresent()) {
            trafficLightRepository.delete(trafficLightOptional.get());
        } else {
            throw new RuntimeException("Traffic Light ID not found!");
        }
    }

    public TrafficLightExhibitionDto updateTrafficLight(Long id, TrafficLightInsertDto dto) {
        TrafficLight existingTrafficLight = trafficLightRepository.findById(id)
                .orElseThrow(() -> new TrafficLightNotFoundException("Traffic Light ID not found!"));
        BeanUtils.copyProperties(dto, existingTrafficLight);
        return new TrafficLightExhibitionDto(trafficLightRepository.save(existingTrafficLight));
    }

    public void switchColor(TrafficLight trafficLight) {
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
        trafficLightRepository.save(trafficLight);
    }

    public void switchColor(Long id, boolean activateEmergency) {
        TrafficLight trafficLight = trafficLightRepository.findById(id)
                .orElseThrow(() -> new TrafficLightNotFoundException("Traffic Light ID not found!"));

        if (activateEmergency) {
            trafficLight.setCurrentColor(TrafficLightColor.GREEN); // Configura o semáforo para verde
            trafficLight.setEmergencyMode(true);  // Ativa o modo de emergência
            trafficLight.setLightTimer(120); // Supõe manter verde por um período prolongado, e.g., 5 minutos
        } else {
            trafficLight.setEmergencyMode(false); // Desativa o modo de emergência
            // Opcional: reconfigura para o estado normal ou ajusta com base nas condições atuais
        }

        trafficLightRepository.save(trafficLight);
    }

    public String reportFault(Long id, boolean faultStatus) {
        TrafficLight trafficLight = trafficLightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Traffic Light not found with ID: " + id));

        trafficLight.setFaultStatus(faultStatus);

        trafficLightRepository.save(trafficLight);

        if (faultStatus) {
            return "Traffic light fault reported, send repair or deactivate!";
        } else {
            return "Traffic light fault resolved, system back to normal!";
        }
    }

}