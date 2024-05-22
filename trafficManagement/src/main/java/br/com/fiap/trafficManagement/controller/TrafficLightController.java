package br.com.fiap.trafficManagement.controller;

import br.com.fiap.trafficManagement.dto.TrafficLightExibhitionDto;
import br.com.fiap.trafficManagement.dto.TrafficLightInsertDto;
import br.com.fiap.trafficManagement.dto.ReturnMessageDto;
import br.com.fiap.trafficManagement.service.TrafficLightService;
import br.com.fiap.trafficManagement.service.TrafficSensorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TrafficLightController {

    @Autowired
    private TrafficLightService trafficLightService;

    @Autowired
    private TrafficSensorService sensorService;

    @PostMapping("/trafficLights")
    @ResponseStatus(HttpStatus.CREATED)
    public TrafficLightExibhitionDto insertTrafficLight(@RequestBody @Valid TrafficLightInsertDto trafficLightInsertDto) {
        return trafficLightService.insertTrafficLight(trafficLightInsertDto);
    }

    @GetMapping("/trafficLights")
    @ResponseStatus(HttpStatus.OK)
    public Page<TrafficLightExibhitionDto> queryAllTrafficLights(Pageable page) {
        return trafficLightService.queryAllTrafficLights(page);
    }

    @GetMapping("/trafficLights/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TrafficLightExibhitionDto queryById(@PathVariable Long id) {
        return trafficLightService.queryById(id);
    }

    @DeleteMapping("/trafficLights/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTrafficLight(@PathVariable Long id) {
        trafficLightService.deleteTrafficLight(id);
    }

    @PutMapping("/trafficLights/activateTrafficLights/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void activateTrafficLight(@PathVariable Long id) {
        trafficLightService.activateTrafficLight(id);
    }

    @PutMapping("/trafficLights/toggleEmergencyMode/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReturnMessageDto toggleEmergencyMode(@PathVariable Long id) {
        return trafficLightService.toggleEmergencyMode(id);
    }

    @PutMapping("/trafficLights/togglePedestrianMode/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReturnMessageDto togglePedestrianMode(@PathVariable Long id) {
        return trafficLightService.togglePedestrianMode(id);
    }

    @PutMapping("/trafficLights/reportTrafficLightFault/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReturnMessageDto reportFault(@PathVariable Long id) {
        return trafficLightService.reportFault(id);
    }

    @PutMapping("/trafficLights/desactivateTrafficLights/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReturnMessageDto desactivateTrafficLight (@PathVariable Long id) {
        return trafficLightService.desactivateTrafficLight(id);
    }

}
