package com.example.weather.forecast.data.service;

import com.example.weather.forecast.data.entity.City;
import com.example.weather.forecast.data.entity.WeatherForecast;
import com.example.weather.forecast.data.model.CityDTO;
import com.example.weather.forecast.data.model.WeatherResponseDTO;
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

    private final Function<CityDTO, City> cityMapperFunction;
    private final Function<WeatherResponseDTO, List<WeatherForecast>> weatherMapperFunction;
    private final CityRepository cityRepository;
    private final WeatherRepository weatherRepository;

    @Override
    public void save(List<WeatherResponseDTO> weatherResponseDTOS) {
        cityRepository.saveAll(weatherResponseDTOS.stream()
                .map(WeatherResponseDTO::getCity)
                .map(cityMapperFunction)
                .collect(Collectors.toList()));

        weatherRepository.saveAll(
                weatherResponseDTOS.stream()
                        .map(weatherMapperFunction)
                        .flatMap(List::stream)
                        .collect(Collectors.toList()));
    }

    @Override
    public List<WeatherForecast> findByCity(String cityName) {
        return weatherRepository.
                findByCityCityName(cityName);
    }
}
