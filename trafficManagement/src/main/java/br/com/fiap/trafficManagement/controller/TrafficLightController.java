package br.com.fiap.trafficManagement.controller;

import br.com.fiap.trafficManagement.dto.TrafficLightExhibitionDto;
import br.com.fiap.trafficManagement.dto.TrafficLightInsertDto;
import br.com.fiap.trafficManagement.model.TrafficLight;
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
    public TrafficLightExhibitionDto insertTrafficLight(@RequestBody @Valid TrafficLightInsertDto trafficLightInsertDto) {
        return service.insertTrafficLight(trafficLightInsertDto);
    }

    @GetMapping("/trafficLights")
    @ResponseStatus(HttpStatus.OK)
    public Page<TrafficLightExhibitionDto> queryAllTrafficLights(Pageable page) {
        return service.queryAllTrafficLights(page);
    }

    @GetMapping("/trafficLights/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TrafficLightExhibitionDto queryById(@PathVariable Long id) {
        return service.queryById(id);
    }

    @DeleteMapping("/trafficLights/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTrafficLight(@PathVariable Long id) {
        service.deleteTrafficLight(id);
    }

    @PutMapping("/trafficLights")
    @ResponseStatus(HttpStatus.OK)
    public TrafficLightExhibitionDto trafficLight(@PathVariable Long id, @RequestBody TrafficLightInsertDto trafficLightInsertDto) {
        return service.updateTrafficLight(id, trafficLightInsertDto);
    }
}
