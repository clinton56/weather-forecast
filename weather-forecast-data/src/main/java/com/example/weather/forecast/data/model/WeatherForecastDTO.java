package com.example.weather.forecast.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherForecastDTO {
    private double temp;
    private double temp_min;
    private double temp_max;
    private double pressure;
    private int humidity;
}
