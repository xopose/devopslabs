package com.lazer.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lazer.backend.model.Airplane;
import com.lazer.backend.pojo.SimplePlane;
import com.lazer.backend.repository.AirplaneRepository;
import com.lazer.backend.service.JasksonService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/airplanes")
public class AirPlaneController {
    private final AirplaneRepository airplaneRepository;
    private final JasksonService jasksonService;

    public AirPlaneController(AirplaneRepository repository, JasksonService jasksonService) {
        this.airplaneRepository = repository;
        this.jasksonService = jasksonService;
    }

    @GetMapping
    public List<Airplane> findAll() {
        return airplaneRepository.findAll();
    }

    @PostMapping
    public Boolean createPlane(@RequestBody String message) throws JsonProcessingException {
        SimplePlane simplePlane = jasksonService.jsonStringToPojo(SimplePlane.class, message);
        System.out.println(simplePlane.toString());
        Airplane airplane = new Airplane(simplePlane.getName(), simplePlane.getProd_year(), false);
        airplaneRepository.save(airplane);
        return true;
    }

    @GetMapping("/{id}")
    public Boolean deletePlane(@PathVariable Long id) {
        airplaneRepository.deleteById(id);
        return true;
    }

    @PostMapping("/flight/take_off/{id}")
    public Boolean toTakeOff(@PathVariable Long id) {
        airplaneRepository.updateAirplaneById(true, id);
        return true;
    }

    @PostMapping("/flight/land/{id}")
    public Boolean toLanding(@PathVariable Long id) {
        airplaneRepository.updateAirplaneById(false, id);
        return true;
    }
}
