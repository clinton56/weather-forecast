package com.example.weather.forecast.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherForecast {
    private String dateTime;
    private double temperature;
    private double minTemperature;
    private double maxTemperature;
    private double pressure;
    private int humidity;
    private double windSpeed;
    private City city;
}


