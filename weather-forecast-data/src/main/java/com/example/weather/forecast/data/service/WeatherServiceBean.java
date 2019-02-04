package com.example.weather.forecast.data.service;

import com.example.weather.forecast.data.entity.City;
import com.example.weather.forecast.data.entity.WeatherForecast;
import com.example.weather.forecast.data.model.CityModel;
import com.example.weather.forecast.data.model.WeatherResponse;
import com.example.weather.forecast.data.repository.CityRepository;
import com.example.weather.forecast.data.repository.WeatherRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class WeatherServiceBean implements WeatherService {

    private final Function<CityModel, City> cityMapperFunction;
    private final Function<WeatherResponse, List<WeatherForecast>> weatherMapperFunction;
    private final CityRepository cityRepository;
    private final WeatherRepository weatherRepository;

    @Override
    public void save(List<WeatherResponse> weatherResponses) {
        cityRepository.saveAll(weatherResponses.stream()
                .map(WeatherResponse::getCity)
                .map(cityMapperFunction)
                .collect(Collectors.toList()));

        weatherRepository.saveAll(
                weatherResponses.stream()
                        .map(weatherMapperFunction)
                        .flatMap(List::stream)
                        .collect(Collectors.toList()));
    }
}
