package com.lazer.backend.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MetricService {

    private final MeterRegistry meterRegistry;

    private Counter getAllPlanesCounter;
    private Counter createNewPlaneCounter;
    private Counter takeOffPlaneCounter;
    private Counter deletePlaneCounter;
    private Counter landPlaneCounter;

    @PostConstruct
    private void postConstruct() {
        getAllPlanesCounter = meterRegistry.counter("get_all_planes");
        createNewPlaneCounter = meterRegistry.counter("create_new_plane");
        deletePlaneCounter = meterRegistry.counter("delete_plane");
        takeOffPlaneCounter = meterRegistry.counter("take_off_plane");
        landPlaneCounter = meterRegistry.counter("land_plane");
    }

    public void incrementGetAllPlanesCounter() {
        getAllPlanesCounter.increment();
    }

    public void incrementCreateNewPlaneCounter() {
        createNewPlaneCounter.increment();
    }

    public void incrementTakeOffPlaneCounter() {
        takeOffPlaneCounter.increment();
    }

    public void incrementDeletePlaneCounter() {
        deletePlaneCounter.increment();
    }

    public void incrementLandPlaneCounter() {
        landPlaneCounter.increment();
    }

}
