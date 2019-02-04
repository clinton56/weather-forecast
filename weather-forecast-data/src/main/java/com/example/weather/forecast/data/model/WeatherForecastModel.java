package com.example.weather.forecast.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherForecastModel {
    private double temp;
    private double temp_min;
    private double temp_max;
    private double pressure;
    private int humidity;
}
