package com.lazer.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
@Table(name = "Airplane")
public class Airplane {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String model;
    private Integer prod_year;
    private Boolean in_flight;

    public Airplane(Long id, String model, Integer prod_year, Boolean in_flight) {
        this.id = id;
        this.model = model;
        this.prod_year = prod_year;
        this.in_flight = in_flight;
    }

    public Airplane(String model, Integer prod_year, Boolean in_flight) {
        this.model = model;
        this.prod_year = prod_year;
        this.in_flight = in_flight;
    }

    public Airplane() {
    }
}
