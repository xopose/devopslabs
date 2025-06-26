package com.lazer.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lazer.backend.metrics.MetricService;
import com.lazer.backend.model.Airplane;
import com.lazer.backend.pojo.SimplePlane;
import com.lazer.backend.repository.AirplaneRepository;
import com.lazer.backend.service.BotRest;
import com.lazer.backend.service.JasksonService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/qqqqairplanes")
public class testController {
    private final AirplaneRepository airplaneRepository;
    private final JasksonService jasksonService;
    private final MetricService metricService;
    public testController(AirplaneRepository repository, JasksonService jasksonService, MetricService metricService) {
        this.airplaneRepository = repository;
        this.jasksonService = jasksonService;
        this.metricService = metricService;
    }

    @GetMapping
    public List<Airplane> findAll() {
        metricService.incrementGetAllPlanesCounter();
        return airplaneRepository.findAll();

    }

    @PostMapping
    public Boolean createPlane(@RequestBody String message) throws JsonProcessingException {
        SimplePlane simplePlane = jasksonService.jsonStringToPojo(SimplePlane.class, message);
        Airplane airplane = new Airplane(simplePlane.getName(), simplePlane.getProd_year(), false);
        airplaneRepository.save(airplane);
        BotRest.send("Создан самолет" + airplane.getModel() + " " + airplane.getProd_year());
        metricService.incrementCreateNewPlaneCounter();
        return true;
    }

    @GetMapping("/{id}")
    public Boolean deletePlane(@PathVariable Long id) {
        airplaneRepository.deleteById(id);
        BotRest.send("Удален самолет" + id);
        metricService.incrementDeletePlaneCounter();
        return true;
    }

    @PostMapping("/flight/take_off/{id}")
    public Boolean toTakeOff(@PathVariable Long id) {
        airplaneRepository.updateAirplaneById(true, id);
        BotRest.send("Взлетел самолет" + id);
        metricService.incrementTakeOffPlaneCounter();
        return true;
    }

    @PostMapping("/flight/land/{id}")
    public Boolean toLanding(@PathVariable Long id) {
        airplaneRepository.updateAirplaneById(false, id);
        BotRest.send("Сел самолет" + id);
        metricService.incrementLandPlaneCounter();
        return true;
    }
}
