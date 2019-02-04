package com.example.weather.forecast.loader.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {
    private int cod;
    private List<WeatherData> list;
    private City city;

    public boolean isSuccess() {
        return 200 == cod;
    }
}
