package br.com.fiap.trafficManagement.controller;

import br.com.fiap.trafficManagement.dto.ReturnMessageDto;
import br.com.fiap.trafficManagement.dto.TrafficSensorExibhitionDto;
import br.com.fiap.trafficManagement.dto.TrafficSensorInsertDto;
import br.com.fiap.trafficManagement.service.TrafficSensorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TrafficSensorController {

    @Autowired
    private TrafficSensorService service;

    @PostMapping("/trafficSensors")
    @ResponseStatus(HttpStatus.CREATED)
    public TrafficSensorExibhitionDto insertTrafficSensor(@RequestBody @Valid TrafficSensorInsertDto trafficSensorInsertDto) {
        return service.insertTrafficSensor(trafficSensorInsertDto);
    }

    @GetMapping("/trafficSensors")
    @ResponseStatus(HttpStatus.OK)
    public Page<TrafficSensorExibhitionDto> queryAllTrafficSensors(Pageable page) {
        return service.queryAllTrafficSensors(page);
    }

    @GetMapping("/trafficSensors/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TrafficSensorExibhitionDto queryById(@PathVariable Long id) {
        return service.queryById(id);
    }

    @DeleteMapping("/trafficSensors/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTrafficSensor(@PathVariable Long id) {
        service.deleteTrafficSensor(id);
    }

    @PutMapping("/activateTrafficSensors/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void activateTrafficSensor (@PathVariable Long id){
        service.activateTrafficSensor(id);
    }

    @PutMapping("/reportTrafficSensorFault/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReturnMessageDto reportFault(@PathVariable Long id){
        return service.reportFault(id);
    }

}
