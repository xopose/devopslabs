package com.lazer.backend.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class SimplePlane {
    @JsonProperty("name")
    private String name;

    @JsonProperty("prod_year")
    private Integer prod_year;

    public Integer getProd_year() {
        return prod_year;
    }

    public void setProd_year(Integer prod_year) {
        this.prod_year = prod_year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
