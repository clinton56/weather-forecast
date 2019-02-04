package com.example.weather.forecast.data.repository;

import com.example.weather.forecast.data.entity.WeatherForecast;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherRepository extends JpaRepository<WeatherForecast, Long> {
}
