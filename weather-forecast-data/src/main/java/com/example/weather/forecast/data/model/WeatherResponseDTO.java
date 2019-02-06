package com.example.weather.forecast.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponseDTO {
    private int cod;
    private List<WeatherDataDTO> list;
    private CityDTO city;

    public boolean isSuccess() {
        return 200 == cod;
    }
}
