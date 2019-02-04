package com.example.weather.forecast.data.service;

import com.example.weather.forecast.data.model.WeatherResponse;

import java.util.List;

public interface WeatherService {

    void save(List<WeatherResponse> weatherResponses);
}
