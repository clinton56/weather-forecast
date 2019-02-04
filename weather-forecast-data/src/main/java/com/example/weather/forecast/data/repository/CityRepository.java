package com.example.weather.forecast.data.repository;

import com.example.weather.forecast.data.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
}
