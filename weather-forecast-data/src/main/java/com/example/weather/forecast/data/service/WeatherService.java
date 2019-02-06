package com.example.weather.forecast.data.service;

import com.example.weather.forecast.data.entity.WeatherForecast;
import com.example.weather.forecast.data.model.WeatherResponseDTO;

import java.util.List;

public interface WeatherService {

    void save(List<WeatherResponseDTO> weatherResponsDTOS);

    List<WeatherForecast> findByCity(String cityName);
}
