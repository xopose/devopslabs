package com.lazer.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lazer.backend.model.Airplane;
import com.lazer.backend.repository.AirplaneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BackendApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AirplaneRepository airplaneRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        airplaneRepository.deleteAll();
    }

    @Test
    public void createPlaneTest() throws Exception {
        String json = """
            {
                "name": "Boeing 747",
                "prod_year": 2010
            }
            """;

        mockMvc.perform(post("/airplanes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        assertThat(airplaneRepository.findAll()).hasSize(1);
        assertThat(airplaneRepository.findAll().get(0).getModel()).isEqualTo("Boeing 747");
    }

    @Test
    public void getAllPlanesTest() throws Exception {
        airplaneRepository.save(new Airplane("Test Plane", 2005, false));

        mockMvc.perform(get("/airplanes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].model").value("Test Plane"));
    }

    @Test
    public void deletePlaneTest() throws Exception {
        Airplane plane = airplaneRepository.save(new Airplane("Plane to delete", 2015, false));

        mockMvc.perform(get("/airplanes/" + plane.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        assertThat(airplaneRepository.findById(plane.getId())).isEmpty();
    }

//    @Test
//    public void takeOffPlaneTest() throws Exception {
//        Airplane plane = airplaneRepository.save(new Airplane("TakeOffPlane", 2018, false));
//
//        mockMvc.perform(post("/airplanes/flight/take_off/" + plane.getId()))
//                .andExpect(status().isOk())
//                .andExpect(content().string("true"));
//
//        assertThat(airplaneRepository.findById(plane.getId()).get().getIn_flight()).isTrue();
//    }
//
//    @Test
//    public void landPlaneTest() throws Exception {
//        Airplane plane = airplaneRepository.save(new Airplane("LandingPlane", 2019, true));
//
//        mockMvc.perform(post("/airplanes/flight/land/" + plane.getId()))
//                .andExpect(status().isOk())
//                .andExpect(content().string("true"));
//
//        assertThat(airplaneRepository.findById(plane.getId()).get().getIn_flight()).isFalse();
//    }
}
