package br.com.fiap.trafficManagement.controller;

import br.com.fiap.trafficManagement.dto.TrafficLightExibhitionDto;
import br.com.fiap.trafficManagement.dto.TrafficLightInsertDto;
import br.com.fiap.trafficManagement.dto.ReturnMessageDto;
import br.com.fiap.trafficManagement.service.TrafficLightService;
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
    private TrafficLightService service;

    @PostMapping("/trafficLights")
    @ResponseStatus(HttpStatus.CREATED)
    public TrafficLightExibhitionDto insertTrafficLight(@RequestBody @Valid TrafficLightInsertDto trafficLightInsertDto) {
        return service.insertTrafficLight(trafficLightInsertDto);
    }

    @GetMapping("/trafficLights")
    @ResponseStatus(HttpStatus.OK)
    public Page<TrafficLightExibhitionDto> queryAllTrafficLights(Pageable page) {
        return service.queryAllTrafficLights(page);
    }

    @GetMapping("/trafficLights/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TrafficLightExibhitionDto queryById(@PathVariable Long id) {
        return service.queryById(id);
    }

    @DeleteMapping("/trafficLights/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTrafficLight(@PathVariable Long id) {
        service.deleteTrafficLight(id);
    }

    @PutMapping("/activateTrafficLights/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void activateTrafficLight (@PathVariable Long id){
        service.activateTrafficLight(id);
    }

    @PutMapping("/toggleEmergencyMode/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReturnMessageDto toggleEmergencyMode(@PathVariable Long id){
        return service.manageEmergencyMode(id);
    }

    @PutMapping("/togglePedestrianMode/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReturnMessageDto togglePedestrianMode(@PathVariable Long id){
        return service.managePedestrianMode(id);
    }

    @PutMapping("/reportTrafficLightFault/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReturnMessageDto reportFault(@PathVariable Long id){
        return service.reportFault(id);
    }

}
