package com.example.weather.forecast.loader.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
class WeatherForecast {
    private double temp;
    private double temp_min;
    private double temp_max;
    private double pressure;
    private int humidity;
}
