package com.example.weather.forecast.data.repository;

import com.example.weather.forecast.data.entity.WeatherForecast;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeatherRepository extends JpaRepository<WeatherForecast, Long> {

    List<WeatherForecast> findByCityCityName(String cityName);
}
