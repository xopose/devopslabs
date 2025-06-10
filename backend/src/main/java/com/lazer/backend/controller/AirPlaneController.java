package com.lazer.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lazer.backend.metrics.MetricService;
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
    private final MetricService metricService;
    public AirPlaneController(AirplaneRepository repository, JasksonService jasksonService, MetricService metricService) {
        this.airplaneRepository = repository;
        this.jasksonService = jasksonService;
        this.metricService = metricService;
    }

    @GetMapping
    public List<Airplane> findAll() {
        metricService.incrementGetMoviesRequestCounter();
        return airplaneRepository.findAll();

    }

    @PostMapping
    public Boolean createPlane(@RequestBody String message) throws JsonProcessingException {
        SimplePlane simplePlane = jasksonService.jsonStringToPojo(SimplePlane.class, message);
        System.out.println(simplePlane.toString());
        Airplane airplane = new Airplane(simplePlane.getName(), simplePlane.getProd_year(), false);
        airplaneRepository.save(airplane);
        metricService.incrementCreateMovieReviewRequestCounter();
        return true;
    }

    @GetMapping("/{id}")
    public Boolean deletePlane(@PathVariable Long id) {
        airplaneRepository.deleteById(id);
        metricService.incrementGetMovieRequestCounter();
        return true;
    }

    @PostMapping("/flight/take_off/{id}")
    public Boolean toTakeOff(@PathVariable Long id) {
        metricService.incrementCreateMovieReviewRequestCounter();
        airplaneRepository.updateAirplaneById(true, id);
        return true;
    }

    @PostMapping("/flight/land/{id}")
    public Boolean toLanding(@PathVariable Long id) {
        airplaneRepository.updateAirplaneById(false, id);
        metricService.incrementEditMovieReviewRequestCounter();
        return true;
    }
}
